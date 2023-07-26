package com.mall.choisinsa.security.service;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.SnsType;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import com.mall.choisinsa.enumeration.member.MemberType;
import com.mall.choisinsa.security.domain.SecurityMember;
import com.mall.choisinsa.security.domain.SecurityMemberSnsConnect;
import com.mall.choisinsa.security.dto.SecurityMemberDto;
import com.mall.choisinsa.security.provider.JwtTokenProvider;
import com.mall.choisinsa.security.repository.SecurityMemberRepository;
import com.mall.choisinsa.util.domain.MemberUtil.MemberValidator;
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
    private final AuthenticationProvider authenticationProvider;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    private final SecurityMemberRepository securityMemberRepository;
    private final SecurityMemberSnsConnectService securityMemberSnsConnectService;


    public String login(MemberType memberType,
                        String loginId,
                        String password) {
        validateLoginInfoOrThrowException(memberType, loginId, password);

        Authentication authenticate = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(loginId, password)
        );
        validateLoginInfoAfterAuth(memberType, authenticate);

        return jwtTokenProvider.createToken(authenticate);
    }

    private void validateLoginInfoAfterAuth(MemberType memberType,
                                            Authentication authenticate) {
        if (!(authenticate instanceof SecurityMemberDto)) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_SUPPORT_AUTHENTICATION);
        }

        SecurityMemberDto memberDto = (SecurityMemberDto) authenticate.getPrincipal();
        if (memberType == null || memberType != memberDto.getMemberType()) {
            throw new ErrorTypeAdviceException(ErrorType.MISMATCH_AUTHORITY);
        }
    }

    public String loginWithSns(SnsType snsType,
                               String snsId) {

        SecurityMemberSnsConnect memberSnsConnect = securityMemberSnsConnectService.findBySnsTypeAndSnsIdOrThrow(snsType, snsId);
        SecurityMember securityMember = findByIdOrThrow(memberSnsConnect.getMemberId());
        return login(MemberType.MEMBER, securityMember.getLoginId(), securityMember.getPassword());
    }

    private void validateLoginInfoOrThrowException(MemberType memberType,
                                                   String loginId,
                                                   String password) {
        if (memberType == null) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_EXISTS_REQUIRED_DATA);
        }

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
