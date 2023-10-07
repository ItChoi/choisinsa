package core.domain.stats;

import core.domain.common.BaseDateTimeEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 상품 판매 통계
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@IdClass(ItemSalesStatsCompositeKey.class)
public class ItemSalesStats extends BaseDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    /**
     * 판매일
     */
    @Id
    @Column
    private LocalDateTime salesDate;

    /**
     * 상품 PK
     */
    @Column
    private Long itemId;

    /**
     * 회원 PK
     */
    @Column
    private Long memberId;

    /**
     * 결제 방법 (CASH: 현금, CARD: 카드)
     */
    @Column
    private String paymentMethod;

    /**
     * 결제 타입, CARD인 경우 (HANA, KB)
     */
    @Column
    private String paymentType;

    @Column
    private String salesType;

    /**
     * 판매 수량
     */
    @Column
    private Integer salesQuantity;

    /**
     * 판매 가격
     */
    @Column
    private Long salesPrice;

    @Column
    private String salesChannel;
}
