package core.domain.item;

import com.mall.choisinsa.security.domain.SecurityMenu;
import core.domain.common.BaseDateTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 상품 옵션 상세
 */
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class ItemOptionDetail extends BaseDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private Long itemOptionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemOptionId", insertable = false, updatable = false)
    private ItemOption itemOption;

    /**
     * 옵션명
     */
    @Setter
    @Column
    private String name;

    /**
     * 옵션 추가 가격
     */
    @Setter
    @Column
    private Long addPrice;

    /**
     * 상품 재고 수량
     */
    @Setter
    @Column
    private int stockQuantity;
}
