package com.mall.choisinsa.security.service;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import com.mall.choisinsa.security.domain.SecurityMember;
import com.mall.choisinsa.util.domain.MemberUtil.MemberValidator;
import com.mall.choisinsa.security.provider.JwtTokenProvider;
import com.mall.choisinsa.security.repository.SecurityMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class SecurityMemberService {
    private final SecurityMemberRepository securityMemberRepository;
    private final AuthenticationProvider authenticationProvider;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;


    public String login(String loginId,
                        String password) {
        validateLoginInfoOrThrowException(loginId, password);
        Authentication authenticate = authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(loginId, password));
        return jwtTokenProvider.createToken(authenticate);
    }

    private void validateLoginInfoOrThrowException(String loginId,
                                                   String password) {

        MemberValidator.validateLoginOrThrow(loginId, password);
    }

    public String encodePassword(String plainText) {
        if (!StringUtils.hasText(plainText)) {
            return null;
        }
        return passwordEncoder.encode(plainText);
    }

    @Transactional(readOnly = true)
    public SecurityMember findByIdOrThrow(Long memberId) {
        return findById(memberId)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.MEMBER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Optional<SecurityMember> findById(Long memberId) {
        return securityMemberRepository.findById(memberId);
    }
}
