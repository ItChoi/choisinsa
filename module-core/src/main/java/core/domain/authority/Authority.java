package core.domain.authority;

import com.mall.choisinsa.domain.BaseDateTimeEntity;
import com.mall.choisinsa.enumeration.security.AuthorityType;
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
    private AuthorityType authority;

    @Column(name = "is_display")
    private boolean display;

    @Column(name = "is_direct_config")
    private boolean directConfig;
}
