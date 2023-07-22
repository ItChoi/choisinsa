package core.domain.item;

import com.mall.choisinsa.enumeration.item.ItemOptionType;
import core.domain.common.BaseDateTimeEntity;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 상품 옵션 정보
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ItemOption extends BaseDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private Long itemId;

    /**
     * 상품 옵션 타입
     */
    @Column
    private ItemOptionType itemOptionType;

    /**
     * 옵션 표시 순서
     */
    @Column
    private int displayOrder;

}
