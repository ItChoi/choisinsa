package core.domain.authority;

import com.mall.choisinsa.domain.BaseDateTimeEntity;
import core.domain.event.EventParticipant;
import core.domain.menu.Menu;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class MenuAccessAuthority extends BaseDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @OneToMany(mappedBy = "userDetailAuthority", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<UserDetailAuthority> userDetailAuthorities;

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

}
