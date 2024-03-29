package core.provider;

import com.mall.choisinsa.common.secret.ConstData;
import core.common.exception.ErrorType;
import core.dto.JwtTokenDto;
import core.dto.SecurityMemberDto;
import core.service.member.UserDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;


@Slf4j
@Component
public class JwtTokenProvider implements InitializingBean {

    @Value("${jwt.access-token.secret}")
    private String accessTokenSecret;

    @Value("${jwt.access-token.validity-in-seconds}")
    private long accessTokenValidityInMilliseconds;

    @Value("${jwt.refresh-token.secret}")
    private String refreshTokenSecret;

    @Value("${jwt.refresh-token.validity-in-seconds}")
    private long refreshTokenValidityInMilliseconds;

    private Key accessTokenkey;
    private Key refreshTokenkey;

    @Override
    public void afterPropertiesSet() {
        byte[] accessKeyBytes = Decoders.BASE64.decode(accessTokenSecret);
        this.accessTokenkey = Keys.hmacShaKeyFor(accessKeyBytes);

        byte[] refreshKeyBytes = Decoders.BASE64.decode(refreshTokenSecret);
        this.refreshTokenkey = Keys.hmacShaKeyFor(refreshKeyBytes);
    }

    public String createToken(Authentication authentication) {
        String authorities = getAuthoritiesWithAuthentication(authentication);
        SecurityMemberDto securityMemberDto = UserDetailsService.toSecurityMemberDtoFromAuthorizedPrinciple(authentication);
        return getJwtsBuilder(securityMemberDto, authorities, accessTokenkey, accessTokenValidityInMilliseconds);
    }

    public String createToken(String refreshToken) {
        return createToken(getAuthentication(refreshToken));
    }

    private String getJwtsBuilder(SecurityMemberDto securityMemberDto,
                                  String authorities,
                                  Key key,
                                  long tokenValidityInMilliseconds) {
        return Jwts.builder()
                .setSubject(securityMemberDto.getLoginId())
                .claim(ConstData.JWT_USER_ID, securityMemberDto.getMemberId())
                .claim(ConstData.AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(getJwtValidity(tokenValidityInMilliseconds))
                .compact();
    }

    private static String getAuthoritiesWithAuthentication(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
    }

    public JwtTokenDto createJwtToken(Authentication authentication) {
        String authorities = getAuthoritiesWithAuthentication(authentication);
        SecurityMemberDto securityMemberDto = UserDetailsService.toSecurityMemberDtoFromAuthorizedPrinciple(authentication);

        return new JwtTokenDto(
                getJwtsBuilder(securityMemberDto, authorities, accessTokenkey, accessTokenValidityInMilliseconds),
                getJwtsBuilder(securityMemberDto, authorities, refreshTokenkey, refreshTokenValidityInMilliseconds)
        );
    }

    public Authentication getAuthentication(String token) {
        return getAuthentication(accessTokenkey, token);
    }

    public Authentication getAuthentication(Key key,
                                            String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();
        Long memberId = claims.get(ConstData.JWT_USER_ID, Long.class);

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(
                        claims.get(ConstData.AUTHORITIES_KEY).toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new UsernamePasswordAuthenticationToken(
                new SecurityMemberDto(memberId, username, "", authorities),
                token,
                authorities
        );
    }

    public Authentication getAuthenticationWithRefresh(String token) {
        return getAuthentication(refreshTokenkey, token);
    }

    public boolean isValidAccessToken(String token) {
        return isValidToken(token, accessTokenkey);
    }

    public boolean isValidRefreshToken(String token) {
        return isValidToken(token, refreshTokenkey);
    }

    private boolean isValidToken(String token,
                                 Key key) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("[JWT ERROR]: {}", ErrorType.WRONG_JWT_TOKEN.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("[JWT ERROR]: {}", ErrorType.EXPIRED_JWT_TOKEN.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("[JWT ERROR]: {}", ErrorType.UNSUPPORTED_JWT_TOKEN.getMessage());
        } catch (Exception e) {
            log.error("[JWT ERROR]: {}", ErrorType.INVALID_JWT_TOKEN.getMessage());
        }

        return false;
    }

    public boolean isExpiredJwtToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(accessTokenkey)
                    .build()
                    .parseClaimsJws(token);

            return false;
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    private Date getJwtValidity(long validityInMilliseconds) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + validityInMilliseconds);
        return validity;
    }
}
