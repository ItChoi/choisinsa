package com.mall.choisinsa.security.filter;

import com.mall.choisinsa.common.secret.ConstData;
import com.mall.choisinsa.enumeration.authority.AuthorizationType;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import com.mall.choisinsa.security.domain.SecurityMember;
import com.mall.choisinsa.security.provider.JwtTokenProvider;
import com.mall.choisinsa.security.service.SecurityMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends GenericFilter {

    public static final String AUTHORIZATION_HEADER = AuthorizationType.AUTHORIZATION.getType();
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = resolveToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();

        if (StringUtils.hasText(token) && jwtTokenProvider.isValidJwtToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("Security Context에 '{}' 인증 정보를 저장했습니다. uri: {}", authentication.getName(), requestURI);
        } else {
            log.debug("[JWT ERROR]: {} / {}", requestURI, ErrorType.WRONG_JWT_TOKEN.getMessage());
        }

        chain.doFilter(request, response);
    }

    // 토큰 정보 꺼내오기.
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(ConstData.AUTHORIZATION_BEARER)) {
            return bearerToken.substring(7);
        }

        return null;
    }

}
