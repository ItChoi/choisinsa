package core.domain.authority;

import core.listener.AdminAuthMenuListener;
import core.domain.common.BaseDateTimeEntity;
import core.domain.menu.Menu;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@EntityListeners(value = {AdminAuthMenuListener.class})
//@EntityListeners(value = {AdminAuthMenuListenerInterface.class})
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class AuthorityMenu extends BaseDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @OneToMany(mappedBy = "authorityMenu", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<MenuDetailAuthority> userDetailAuthorities;

    @Column
    private Long menuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menuId", insertable = false, updatable = false)
    private Menu menu;

    @Column
    private Long authorityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorityId", insertable = false, updatable = false)
    private Authority authority;

    @Column
    private Boolean isUse; // 메뉴 사용, 미사용 -> 미사용시 메뉴 포함 상세 api url들도 접근이 불가능 해야함.

}
