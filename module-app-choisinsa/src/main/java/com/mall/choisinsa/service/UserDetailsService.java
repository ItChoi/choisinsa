package com.mall.choisinsa.service;

import com.mall.choisinsa.dto.SecurityMemberDto;
import core.common.enumeration.SnsType;
import core.common.exception.ErrorType;
import core.common.exception.ErrorTypeAdviceException;
import core.domain.member.Member;
import core.domain.member.MemberSnsConnect;
import core.repository.authority.MemberAuthorityRepository;
import core.repository.member.MemberRepository;
import core.repository.member.MemberSnsConnectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final MemberRepository memberRepository;
    private final MemberAuthorityRepository memberAuthorityRepository;
    private final MemberSnsConnectRepository memberSnsConnectRepository;

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

        Member member = memberRepository.findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorType.MEMBER_NOT_FOUND.getMessage()));

        Long memberId = member.getId();

        List<SimpleGrantedAuthority> authorities = memberAuthorityRepository.findAllByMemberId(memberId).stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority().getType().getText()))
                .collect(Collectors.toList());

        return new SecurityMemberDto(true, member, authorities);
    }

    @Transactional(readOnly = true)
    public UserDetails loadUserByMemberId(Long memberId) throws UsernameNotFoundException {
        if (memberId == null) {
            throw new UsernameNotFoundException(ErrorType.NOT_EXISTS_REQUIRED_DATA.getMessage());
        }

        Member member = findByIdOrThrow(memberId);
        List<SimpleGrantedAuthority> authorities = memberAuthorityRepository.findAllByMemberId(memberId).stream()
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

        Member member = getMemberWithMemberSnsConnectOrThrow(snsType, snsId);

        Long memberId = member.getId();
        List<SimpleGrantedAuthority> authorities = memberAuthorityRepository.findAllByMemberId(memberId).stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority().getType().getText()))
                .collect(Collectors.toList());

        return new SecurityMemberDto(false, member, authorities);
    }



    private Member findByIdOrThrow(Long memberId) {
        return findMemberById(memberId)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.MEMBER_NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public Optional<Member> findMemberById(Long memberId) {
        return memberRepository.findById(memberId);
    }

    @Transactional(readOnly = true)
    public Optional<MemberSnsConnect> findMemberSnsConnectBySnsTypeAndSnsId(SnsType snsType,
                                                                            String snsId) {

        return memberSnsConnectRepository.findBySnsTypeAndSnsId(snsType, snsId);
    }

    @Transactional(readOnly = true)
    public Member getMemberWithMemberSnsConnectOrThrow(SnsType snsType,
                                                               String snsId) {
        MemberSnsConnect memberSnsConnect = findMemberSnsConnectBySnsTypeAndSnsId(snsType, snsId)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.BAD_REQUEST));

        return findMemberById(memberSnsConnect.getMemberId())
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.BAD_REQUEST));
    }
}