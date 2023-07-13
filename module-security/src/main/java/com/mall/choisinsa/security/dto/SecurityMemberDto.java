package com.mall.choisinsa.security.dto;

import com.mall.choisinsa.security.domain.SecurityMember;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class SecurityMemberDto extends User {
    private Long memberId;
    private String loginId;
    @Setter
    private String password;
    private String nickName;

    public SecurityMemberDto(Long memberId,
                             String loginId,
                             String password,
                             String nickName,
                             Collection<? extends GrantedAuthority> authorities) {
        super(loginId, password, authorities);
        this.memberId = memberId;
        this.loginId = loginId;
        this.password = password;
        this.nickName = nickName;
    }

    public SecurityMemberDto(Long memberId,
                             String loginId,
                             String password,
                             Collection<? extends GrantedAuthority> authorities) {
        super(loginId, password, authorities);
        this.memberId = memberId;
        this.loginId = loginId;
        this.password = password;
    }
}
