package com.mall.choisinsa.security.provider;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.common.secret.ConstData;
import com.mall.choisinsa.enumeration.SnsType;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import com.mall.choisinsa.security.domain.SecurityMember;
import com.mall.choisinsa.security.domain.SecurityMemberSnsConnect;
import com.mall.choisinsa.security.dto.SecurityMemberDto;
import com.mall.choisinsa.security.service.SecurityUserDetailsService;
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
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

import static com.mall.choisinsa.security.service.SecurityUserDetailsService.toSecurityMemberDtoFromAuthorizedPrinciple;

@Slf4j
@Component
public class JwtTokenProvider implements InitializingBean {


    private final String secret;
    private final long tokenValidityInMilliseconds;
    private Key key;

    public JwtTokenProvider(@Value("${jwt.member.secret}") String secret,
                            @Value("${jwt.member.token-validity-in-seconds}") long tokenValidityInMilliseconds) {
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInMilliseconds;
    }

    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        SecurityMemberDto securityMemberDto = toSecurityMemberDtoFromAuthorizedPrinciple(authentication);
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(ConstData.JWT_USER_ID, securityMemberDto.getMemberId())
                .claim(ConstData.AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(getJwtValidity())
                .compact();
    }

    public Authentication getAuthentication(String token) {
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

    public boolean isValidJwtToken(String token) {
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

    private Date getJwtValidity() {
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);
        return validity;
    }
}
