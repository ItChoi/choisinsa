package com.mall.choisinsa.security.service;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.SnsType;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import com.mall.choisinsa.security.domain.SecurityMember;
import com.mall.choisinsa.security.domain.SecurityMemberSnsConnect;
import com.mall.choisinsa.security.dto.SecurityMemberDto;
import com.mall.choisinsa.security.repository.SecurityMemberAuthorityRepository;
import com.mall.choisinsa.security.repository.SecurityMemberRepository;
import com.mall.choisinsa.security.repository.SecurityMemberSnsConnectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private final SecurityMemberRepository securityMemberRepository;
    private final SecurityMemberAuthorityRepository securityMemberAuthorityRepository;
    private final SecurityMemberSnsConnectRepository securityMemberSnsConnectRepository;

    public static SecurityMemberDto toSecurityMemberDtoFromAuthorizedPrinciple(Authentication authentication) {
        validateAuthorizedPrinciple(authentication);
        return (SecurityMemberDto) authentication.getPrincipal();
    }

    public static void validateAuthorizedPrinciple(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_EXISTS_REQUIRED_DATA);
        }

        if (!(authentication.getPrincipal() instanceof SecurityMemberDto)) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_SUPPORT_AUTHENTICATION);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!StringUtils.hasText(username)) {
            throw new UsernameNotFoundException(ErrorType.NOT_EXISTS_REQUIRED_DATA.getMessage());
        }

        SecurityMember member = securityMemberRepository.findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorType.MEMBER_NOT_FOUND.getMessage()));

        Long memberId = member.getId();

        List<SimpleGrantedAuthority> authorities = securityMemberAuthorityRepository.findAllByMemberId(memberId).stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority().getType().getText()))
                .collect(Collectors.toList());

        return new SecurityMemberDto(true, member, authorities);
    }

    @Transactional(readOnly = true)
    public UserDetails loadUserByMemberId(Long memberId) throws UsernameNotFoundException {
        if (memberId == null) {
            throw new UsernameNotFoundException(ErrorType.NOT_EXISTS_REQUIRED_DATA.getMessage());
        }

        SecurityMember member = findByIdOrThrow(memberId);
        List<SimpleGrantedAuthority> authorities = securityMemberAuthorityRepository.findAllByMemberId(memberId).stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority().getType().getText()))
                .collect(Collectors.toList());

        return new SecurityMemberDto(true, member, authorities);
    }

    @Transactional(readOnly = true)
    public UserDetails loadSnsUserBySnsTypeAndSnsId(SnsType snsType,
                                                    String snsId) throws UsernameNotFoundException {
        if (!StringUtils.hasText(snsId) || snsType == null) {
            throw new UsernameNotFoundException(ErrorType.NOT_EXISTS_REQUIRED_DATA.getMessage());
        }

        SecurityMember member = getMemberWithMemberSnsConnectOrThrow(snsType, snsId);

        Long memberId = member.getId();
        List<SimpleGrantedAuthority> authorities = securityMemberAuthorityRepository.findAllByMemberId(memberId).stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority().getType().getText()))
                .collect(Collectors.toList());

        return new SecurityMemberDto(false, member, authorities);
    }



    private SecurityMember findByIdOrThrow(Long memberId) {
        return findMemberById(memberId)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.MEMBER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Optional<SecurityMember> findMemberById(Long memberId) {
        return securityMemberRepository.findById(memberId);
    }

    @Transactional(readOnly = true)
    public Optional<SecurityMemberSnsConnect> findMemberSnsConnectBySnsTypeAndSnsId(SnsType snsType,
                                                                                    String snsId) {

        return securityMemberSnsConnectRepository.findBySnsTypeAndSnsId(snsType, snsId);
    }

    @Transactional(readOnly = true)
    public SecurityMember getMemberWithMemberSnsConnectOrThrow(SnsType snsType,
                                                               String snsId) {
        SecurityMemberSnsConnect memberSnsConnect = findMemberSnsConnectBySnsTypeAndSnsId(snsType, snsId)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.BAD_REQUEST));

        return findMemberById(memberSnsConnect.getMemberId())
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.BAD_REQUEST));
    }
}