package core.domain.item;

import core.common.enumeration.item.ItemStatus;
import core.common.enumeration.item.TargetType;
import core.domain.common.BaseDateTimeEntity;
import lombok.*;

import javax.persistence.*;

/**
 * 상품
 */
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Item extends BaseDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private Long itemCategoryId;

    @Column
    private Long brandId;

    @Enumerated(EnumType.STRING)
    @Column
    private ItemStatus status;

    /**
     * 사용 타겟 (남성, 여성, 기타, ...)
     */
    @Setter
    @Enumerated(EnumType.STRING)
    @Column
    private TargetType targetType;

    /**
     * 상품명 (영문)
     */
    @Setter
    @Column
    private String nameEn;

    /**
     * 상품명 (국문)
     */
    @Setter
    @Column
    private String nameKo;

    /**
     * 상품 가격
     */
    @Column
    private Long price;

    /**
     * 상품 이미지 경로
     */
    @Setter
    @Column
    private String fileUrl;

    /**
     * 상품 이미지명
     */
    @Setter
    @Column
    private String filename;

    /**
     * 총 상품 재고 수량(모든 옵션 수량도 모두 포함)
     */
    @Setter
    @Column
    private int totalStockQuantity;

    public boolean canPurchaseItemStatus() {
        return this.totalStockQuantity > 0 && ItemStatus.canPurchaseItemStatus(this.status);
    }

    public boolean isDisplayItemStatus() {
        return ItemStatus.isDisplayItemStatus(this.status);
    }
}
