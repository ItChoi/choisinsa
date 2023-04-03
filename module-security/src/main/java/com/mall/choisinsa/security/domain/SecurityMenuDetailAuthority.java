package com.mall.choisinsa.security.domain;

import com.mall.choisinsa.enumeration.authority.UserDetailAuthorityType;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "menu_detail_authority")
@Entity
public class SecurityMenuDetailAuthority {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private Long authorityMenuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorityMenuId", insertable = false, updatable = false)
    private SecurityAuthorityMenu authorityMenu;

    @Enumerated(EnumType.STRING)
    @Column
    private UserDetailAuthorityType type;
}
