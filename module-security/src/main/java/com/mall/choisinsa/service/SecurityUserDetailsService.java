package com.mall.choisinsa.service;

import com.mall.choisinsa.domain.SecurityMember;
import com.mall.choisinsa.dto.SecurityMemberDto;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import com.mall.choisinsa.repository.SecurityMemberAuthorityRepository;
import com.mall.choisinsa.repository.SecurityMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private final SecurityMemberRepository securityMemberRepository;
    private final SecurityMemberAuthorityRepository securityMemberAuthorityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!StringUtils.hasText(username)) throw new UsernameNotFoundException(ErrorType.MEMBER_NOT_FOUND.getMessage());

        SecurityMember member = securityMemberRepository.findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException(ErrorType.MEMBER_NOT_FOUND.getMessage()));

        Long memberId = member.getId();

        List<SimpleGrantedAuthority> authorities = securityMemberAuthorityRepository.findAllByMemberId(memberId).stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getAuthority().getAuthority().getText()))
                .collect(Collectors.toList());

        return new SecurityMemberDto(memberId, member.getLoginId(), member.getPassword(), member.getNickName(), authorities);
    }

}
