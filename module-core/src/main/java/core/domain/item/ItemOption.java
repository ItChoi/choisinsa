package core.domain.item;

import core.common.enumeration.item.ItemOptionType;
import core.domain.common.BaseDateTimeEntity;
import lombok.*;

import javax.persistence.*;
import java.util.*;

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

    @OneToMany(mappedBy = "itemOption", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ItemOptionDetail> itemOptionDetails;

    /**
     * 상품 옵션 타입
     */
    @Setter
    @Enumerated(EnumType.STRING)
    @Column
    private ItemOptionType itemOptionType;

    /**
     * 옵션 표시 순서
     */
    @Setter
    @Column
    private int displayOrder;

    public void addItemOptionDetail(ItemOptionDetail itemOptionDetail) {
        if (this.itemOptionDetails == null) {
            this.itemOptionDetails = new ArrayList<>();
        }

        this.itemOptionDetails.add(itemOptionDetail);
        itemOptionDetail.addItemOption(this);
    }

    public void addItemOptionDetails(Collection<ItemOptionDetail> itemOptionDetails) {
        for (ItemOptionDetail itemOptionDetail : itemOptionDetails) {
            addItemOptionDetail(itemOptionDetail);
        }
    }
}
