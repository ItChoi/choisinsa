package com.mall.choisinsa.security.domain;

import com.mall.choisinsa.enumeration.authority.AuthorityType;
import lombok.*;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "authority")
public class SecurityAuthority {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column
    private AuthorityType type;

    @Column
    private Boolean isDisplay;

    @Column
    private Boolean isUseMenuAuthority;

    @Column
    private Boolean isAdminAuthority;
}
