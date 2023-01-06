package core.domain.menu;

import com.mall.choisinsa.domain.BaseDateTimeEntity;
import com.mall.choisinsa.enumeration.menu.MenuType;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Menu extends BaseDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column
    private MenuType type;

    @Column
    private Long parentId;

    @Column
    private Integer depth;

    @Column
    private Integer displayOrder;

    @Column
    private String apiUrl;
}
