package com.mall.choisinsa.security.domain;

import lombok.*;

import javax.persistence.*;


@Builder
@AllArgsConstructor
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
}


