package core.domain.authority;

import core.domain.common.BaseDateTimeEntity;
import com.mall.choisinsa.enumeration.authority.AuthorityType;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Authority extends BaseDateTimeEntity {
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
