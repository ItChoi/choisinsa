package com.mall.choisinsa.service;

import com.mall.choisinsa.dto.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import com.mall.choisinsa.provider.JwtTokenProvider;
import com.mall.choisinsa.repository.SecurityMemberRepository;
import com.mall.choisinsa.util.regex.MemberRegEx;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service
public class SecurityMemberService {
    private final SecurityMemberRepository securityMemberRepository;
    private final AuthenticationProvider authenticationProvider;
    private final JwtTokenProvider jwtTokenProvider;


    public String login(String loginId, String password) throws ErrorTypeAdviceException {
        validateLoginInfoOrThrowException(loginId, password);
        Authentication authenticate = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(loginId, password));
        return jwtTokenProvider.createToken(authenticate);
    }

    private void validateLoginInfoOrThrowException(String loginId, String password) throws ErrorTypeAdviceException {
        if (!StringUtils.hasText(loginId) || !StringUtils.hasText(password)) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }

        if (!MemberRegEx.isAvailableLoginId(loginId)) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_AVAILABLE_LOGIN_ID);
        }

        if (!MemberRegEx.isAvailablePassword(password)) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_AVAILABLE_PASSWORD);
        }
    }
}
