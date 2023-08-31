package core.dto.client.response.item;

import com.mall.choisinsa.enumeration.item.ItemOptionType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ItemOptionResponseDto {
    private Long id;
    private Long itemId;
    private ItemOptionType itemOptionType;
    private int displayOrder;
    private List<ItemOptionDetailResponseDto> itemOptionDetails;
}
