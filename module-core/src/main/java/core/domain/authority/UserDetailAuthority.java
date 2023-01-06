package core.domain.authority;

import com.mall.choisinsa.domain.BaseDateTimeEntity;
import com.mall.choisinsa.enumeration.authority.UserDetailAuthorityType;
import core.domain.menu.Menu;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class UserDetailAuthority extends BaseDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private Long menuAccessAuthorityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menuAccessAuthorityId", insertable = false, updatable = false)
    private UserDetailAuthority userDetailAuthority;

    @Enumerated(EnumType.STRING)
    @Column
    private UserDetailAuthorityType type;
}
