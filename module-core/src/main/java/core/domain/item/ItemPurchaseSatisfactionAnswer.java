package core.domain.item;

import core.domain.common.BaseDateTimeEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 상품 구매 만족도 유저 답변
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ItemPurchaseSatisfactionAnswer extends BaseDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private Long itemPurchaseSatisfactionId;

    @Column
    private Long purchaseSatisfactionQuestionId;
}
