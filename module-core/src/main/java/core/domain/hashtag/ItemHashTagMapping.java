package core.domain.hashtag;

import core.common.enumeration.hashtag.ItemHashTagType;
import core.domain.common.BaseDateTimeEntity;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class ItemHashTagMapping extends BaseDateTimeEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column
    private Long itemId;

    @Column
    private Long hashTagId;

    /**
     * 상품 해시태그 표시 순서
     */
    @Setter
    @Column
    private int displayOrder;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column
    private ItemHashTagType type;
}
