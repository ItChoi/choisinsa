package com.mall.choisinsa.security.domain;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "authority_menu")
@Entity
public class SecurityAuthorityMenu {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @OneToMany(mappedBy = "authorityMenu", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<SecurityMenuDetailAuthority> userDetailAuthorities;

    @Column
    private Long menuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menuId", insertable = false, updatable = false)
    private SecurityMenu menu;

    @Column
    private Long authorityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorityId", insertable = false, updatable = false)
    private SecurityAuthority authority;

    @Column
    private Boolean isUse; // 메뉴 사용, 미사용 -> 미사용시 메뉴 포함 상세 api url들도 접근이 불가능 해야함.
}
