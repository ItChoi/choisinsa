package core.repository.item;

import core.dto.client.request.item.ItemDetailRequestDto;
import core.dto.client.response.item.ItemDetailInfoResponseDto;
import core.dto.client.response.item.ItemResponseDto;

public interface ItemRepositoryCustom {
    ItemResponseDto findItemResponseDtoBy(Long itemId, ItemDetailRequestDto requestDto);
}
