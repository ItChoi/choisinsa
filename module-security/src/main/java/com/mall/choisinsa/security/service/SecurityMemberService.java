package com.mall.choisinsa.security.service;

import com.mall.choisinsa.util.MemberUtil.MemberValidatorThrow;
import com.mall.choisinsa.security.provider.JwtTokenProvider;
import com.mall.choisinsa.security.repository.SecurityMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SecurityMemberService {
    private final SecurityMemberRepository securityMemberRepository;
    private final AuthenticationProvider authenticationProvider;
    private final JwtTokenProvider jwtTokenProvider;


    public String login(String loginId,
                        String password) {

        validateLoginInfoOrThrowException(loginId, password);
        Authentication authenticate = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(loginId, password));
        return jwtTokenProvider.createToken(authenticate);
    }

    private void validateLoginInfoOrThrowException(String loginId,
                                                   String password) {

        MemberValidatorThrow.validateMemberBy(loginId, password);
    }
}
