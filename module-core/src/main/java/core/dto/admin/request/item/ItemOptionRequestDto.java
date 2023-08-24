package core.dto.admin.request.item;

import com.mall.choisinsa.enumeration.item.ItemOptionType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Setter
@Getter
public class ItemOptionRequestDto {
    private Long itemOptionId;
    private ItemOptionType itemOptionType;
    private int displayOrder;
    private List<ItemOptionDetailRequestDto> itemOptionDetails;

    public boolean isRegistrableData() {
        return itemOptionType != null
                && displayOrder > 0
                && !CollectionUtils.isEmpty(itemOptionDetails);
    }
}