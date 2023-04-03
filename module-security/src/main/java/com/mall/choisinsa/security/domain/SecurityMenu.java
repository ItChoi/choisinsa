package com.mall.choisinsa.security.domain;

import com.mall.choisinsa.enumeration.menu.MenuType;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "menu")
@Entity
public class SecurityMenu {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @OneToMany(mappedBy = "menu", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<SecurityMenuIncludeDetailApiUrl> menuIncludeDetailApiUrls;

    @Column
    private Long parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId", insertable = false, updatable = false)
    private SecurityMenu parent;

    @OneToMany(mappedBy = "parent",orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<SecurityMenu> children;

    @Enumerated(EnumType.STRING)
    @Column
    private MenuType type;

    @Column
    private String name;

    @Column
    private Integer depth;

    @Column
    private Integer displayOrder;

    @Column
    private Boolean isDisplay;

}
