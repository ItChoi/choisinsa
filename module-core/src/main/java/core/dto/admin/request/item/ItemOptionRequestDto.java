package core.dto.admin.request.item;

import com.mall.choisinsa.enumeration.item.ItemOptionType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Getter
public class ItemOptionRequestDto {
    private Long itemOptionId;
    @Setter
    private ItemOptionType itemOptionType;
    @Setter
    private int displayOrder;
    @Setter
    private List<ItemOptionDetailRequestDto> itemOptionDetails;

    public boolean isRegistrableData() {
        return itemOptionType != null
                && displayOrder > 0
                && !CollectionUtils.isEmpty(itemOptionDetails);
    }
}