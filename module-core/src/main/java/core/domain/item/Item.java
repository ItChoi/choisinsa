package core.domain.item;

import core.domain.common.BaseDateTimeEntity;
import lombok.*;

import javax.persistence.*;

/**
 * 상품
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Item extends BaseDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private Long itemCategoryId;

    @Column
    private Long brandId;

    /**
     * 사용 타겟 (남성, 여성, 모두, 아기, ...)
     */
    @Column
    private String useTarget;

    /**
     * 상품명 (영문)
     */
    @Column
    private String nameEn;

    /**
     * 상품명 (국문)
     */
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
    @Column
    private String fileUrl;

    /**
     * 상품 이미지명
     */
    @Column
    private String filename;

    /**
     * 총 상품 재고 수량(모든 옵션 수량도 모두 포함)
     */
    @Column
    private int totalStockQuantity;
}
