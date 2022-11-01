package com.mall.choisinsa.provider;

import com.mall.choisinsa.dto.SecurityMemberDto;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import com.mall.choisinsa.service.SecurityUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import javax.transaction.NotSupportedException;

@RequiredArgsConstructor
@Configuration
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final SecurityUserDetailsService securityUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!supports(authentication.getClass())) {
            throw new AuthenticationServiceException(ErrorType.NOT_SUPPORT_AUTHENTICATION.getMessage());
        }

        String loginId = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();

        SecurityMemberDto securityMemberDto = (SecurityMemberDto) securityUserDetailsService.loadUserByUsername(loginId);

        validateMatchPasswordOrThrowException(password, securityMemberDto.getPassword());

        securityMemberDto.setPassword(null);

        return new UsernamePasswordAuthenticationToken(securityMemberDto, null, securityMemberDto.getAuthorities());
    }

    private void validateMatchPasswordOrThrowException(String requestPw, String savedPw) {
        if ((!StringUtils.hasText(requestPw) || !StringUtils.hasText(savedPw)) || !passwordEncoder.matches(requestPw, savedPw)) {
            throw new BadCredentialsException(ErrorType.MISMATCH_REQUEST.getMessage());
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(UsernamePasswordAuthenticationToken.class);
    }

}
