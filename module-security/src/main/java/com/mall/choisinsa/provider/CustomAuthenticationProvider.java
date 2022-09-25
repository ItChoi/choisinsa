package com.mall.choisinsa.provider;

import com.mall.choisinsa.service.SecurityMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@RequiredArgsConstructor
@Configuration
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final SecurityMemberService securityMemberService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {

        return false;
    }
}
