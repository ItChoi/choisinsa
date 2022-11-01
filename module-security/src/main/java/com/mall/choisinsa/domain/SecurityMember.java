package com.mall.choisinsa.domain;

import com.mall.choisinsa.enumeration.member.LoginType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "member")
public class SecurityMember extends BaseDateTimeEntity {

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
    private String recommenderLoginId;
    @Column
    private String nickName;
    @Column
    private String phoneNumber;
    @Column
    private String profileFileUrl;
    @Enumerated(EnumType.STRING)
    @Column
    private LoginType loginType;

}


