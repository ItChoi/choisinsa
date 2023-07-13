package com.mall.choisinsa.security.domain;

import com.mall.choisinsa.enumeration.SnsType;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "member_sns_connect")
public class SecurityMemberSnsConnect {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private Long memberId;

    @Column
    private String snsId;

    @Enumerated(EnumType.STRING)
    @Column
    private SnsType snsType;
}
