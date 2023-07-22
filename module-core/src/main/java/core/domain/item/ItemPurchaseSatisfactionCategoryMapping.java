package core.domain.item;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 상품 구매 만족도 카테고리 매핑
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ItemPurchaseSatisfactionCategoryMapping {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Column(name = "purchase_satisfaction_category_id", nullable = false)
    private Long purchaseSatisfactionCategoryId;

    /**
     * 생성일
     */
    @Column(name = "created_dt", nullable = false)
    private LocalDateTime createdDt;

    /**
     * 수정일
     */
    @Column(name = "updated_dt")
    private LocalDateTime updatedDt;

}
