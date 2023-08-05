package core.dto.admin.request.item;

import com.mall.choisinsa.enumeration.item.ItemOptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ItemOptionRequestDto {
    private ItemOptionType itemOptionType;
    private int displayOrder;
    private List<ItemOptionDetailRequestDto> itemOptionDetails;
}