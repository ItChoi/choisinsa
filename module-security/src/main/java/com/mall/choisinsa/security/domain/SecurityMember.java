package com.mall.choisinsa.security.domain;

import com.mall.choisinsa.enumeration.member.MemberStatus;
import com.mall.choisinsa.enumeration.member.MemberType;
import lombok.*;

import javax.persistence.*;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "member")
public class SecurityMember {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private String loginId;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    @Column
    private MemberStatus status;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String nickName;

    @Column
    private String phoneNumber;

    @Column
    private String profileFileUrl;

    @Enumerated(EnumType.STRING)
    @Column
    private MemberType memberType;

}


