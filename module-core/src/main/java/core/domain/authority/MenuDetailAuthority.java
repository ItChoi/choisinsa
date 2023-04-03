package core.domain.authority;

import com.mall.choisinsa.enumeration.authority.UserDetailAuthorityType;
import com.mall.choisinsa.security.listener.profile.AdminAuthMenuListener;
import core.domain.common.BaseDateTimeEntity;
import lombok.*;

import javax.persistence.*;

@EntityListeners(value = {AdminAuthMenuListener.class})
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class MenuDetailAuthority extends BaseDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private Long authorityMenuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authorityMenuId", insertable = false, updatable = false)
    private AuthorityMenu authorityMenu;

    @Enumerated(EnumType.STRING)
    @Column
    private UserDetailAuthorityType type;
}
