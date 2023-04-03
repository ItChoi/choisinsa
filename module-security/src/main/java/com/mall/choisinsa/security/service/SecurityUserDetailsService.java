package com.mall.choisinsa.security.service;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import com.mall.choisinsa.security.domain.SecurityMember;
import com.mall.choisinsa.security.dto.SecurityMemberDto;
import com.mall.choisinsa.security.repository.SecurityMemberAuthorityRepository;
import com.mall.choisinsa.security.repository.SecurityMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private final SecurityMemberRepository securityMemberRepository;
    private final SecurityMemberAuthorityRepository securityMemberAuthorityRepository;

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
        if (!StringUtils.hasText(username)) throw new UsernameNotFoundException(ErrorType.MEMBER_NOT_FOUND.getMessage());

        SecurityMember member = securityMemberRepository.findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorType.MEMBER_NOT_FOUND.getMessage()));

        Long memberId = member.getId();

        List<SimpleGrantedAuthority> authorities = securityMemberAuthorityRepository.findAllByMemberId(memberId).stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority().getType().getText()))
                .collect(Collectors.toList());

        return new SecurityMemberDto(memberId, member.getLoginId(), member.getPassword(), member.getNickName(), authorities);
    }

    public UserDetails loadUserByMemberId(Long memberId) throws UsernameNotFoundException {
        if (memberId == null) throw new UsernameNotFoundException(ErrorType.MEMBER_NOT_FOUND.getMessage());

        SecurityMember member = findByIdOrThrow(memberId);
        List<SimpleGrantedAuthority> authorities = securityMemberAuthorityRepository.findAllByMemberId(memberId).stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority().getType().getText()))
                .collect(Collectors.toList());

        return new SecurityMemberDto(memberId, member.getLoginId(), member.getPassword(), member.getNickName(), authorities);
    }

    private SecurityMember findByIdOrThrow(Long memberId) {
        return findById(memberId)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.MEMBER_NOT_FOUND));
    }

    private Optional<SecurityMember> findById(Long memberId) {
        return securityMemberRepository.findById(memberId);
    }

}
