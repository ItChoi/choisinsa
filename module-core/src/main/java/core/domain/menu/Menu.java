package core.domain.menu;

import core.domain.common.BaseDateTimeEntity;
import core.common.enumeration.menu.MenuType;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Menu extends BaseDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @OneToMany(mappedBy = "menu", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<MenuIncludeDetailApiUrl> menuIncludeDetailApiUrls;

    @Column
    private Long parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parentId", insertable = false, updatable = false)
    private Menu parent;

    @OneToMany(mappedBy = "parent",orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<Menu> children;

    @Enumerated(EnumType.STRING)
    @Column
    private MenuType type;

    /**
     * 메뉴 코드
     */
    @Column
    private String code;

    @Column
    private String name;

    @Column
    private int depth;

    @Column
    private int displayOrder;

    @Column
    private Boolean isDisplay;
}
