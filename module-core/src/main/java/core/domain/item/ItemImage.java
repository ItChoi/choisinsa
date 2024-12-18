package core.domain.item;

import core.common.enumeration.item.ItemImageType;
import core.domain.common.BaseDateTimeEntity;
import lombok.*;

import javax.persistence.*;

/**
 * 상품 이미지 정보
 */
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class ItemImage extends BaseDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private Long itemId;

    /**
     * 상품 이미지 타입
     */
    @Enumerated(EnumType.STRING)
    @Column
    private ItemImageType itemImageType;

}
