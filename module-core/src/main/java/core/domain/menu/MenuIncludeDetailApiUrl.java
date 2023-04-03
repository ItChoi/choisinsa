package core.domain.menu;

import core.domain.common.BaseDateTimeEntity;
import com.mall.choisinsa.enumeration.menu.MenuIncludeApiUrlStatus;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class MenuIncludeDetailApiUrl extends BaseDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private Long menuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menuId", insertable = false, updatable = false)
    private Menu menu;

    @Column
    private String apiUrl;

    /*@Enumerated(EnumType.STRING)
    @Column
    private MenuIncludeApiUrlStatus status;*/
}
