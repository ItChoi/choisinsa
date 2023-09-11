package core.dto.client.response.item;


import core.domain.item.ItemCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemCountPerItemCategoryResponseDto {
    private Long itemCategoryId;
    private Long itemCount;
}
