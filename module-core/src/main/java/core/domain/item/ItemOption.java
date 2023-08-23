package core.domain.item;

import com.mall.choisinsa.enumeration.item.ItemOptionType;
import core.domain.common.BaseDateTimeEntity;
import core.domain.menu.Menu;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 상품 옵션 정보
 */
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class ItemOption extends BaseDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private Long itemId;

    @Setter
    @OneToMany(mappedBy = "itemOption", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<ItemOptionDetail> itemOptionDetails;

    /**
     * 상품 옵션 타입
     */
    @Setter
    @Column
    private ItemOptionType itemOptionType;

    /**
     * 옵션 표시 순서
     */
    @Setter
    @Column
    private int displayOrder;

}
