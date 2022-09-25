package com.mall.choisinsa.service;

import com.mall.choisinsa.repository.SecurityMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Service
public class SecurityMemberService implements UserDetailsService {
    private final SecurityMemberRepository securityMemberRepository;

    // username = member.login_id
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!StringUtils.hasText(username)) {

        }



        return null;
    }
}
