package core.repository.item;

import core.dto.client.response.item.ItemCountPerItemCategoryResponseDto;

import java.util.Collection;
import java.util.List;

public interface ItemCategoryRepositoryCustom {
    List<ItemCountPerItemCategoryResponseDto> findItemCountPerItemCategoryResponseDtoAllByItemCategoryIds(Collection<Long> itemCategoryIds);
}
