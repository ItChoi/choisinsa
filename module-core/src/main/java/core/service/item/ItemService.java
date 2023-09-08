package core.service.item;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import com.mall.choisinsa.enumeration.hashtag.ItemHashTagType;
import com.mall.choisinsa.enumeration.item.ItemStatus;
import core.domain.item.Item;
import core.dto.client.request.item.ItemDetailRequestDto;
import core.dto.client.response.item.*;
import core.repository.item.ItemRepository;
import core.service.hashtag.ItemHashTagMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemOptionService itemOptionService;
    private final ItemEditorInfoService itemEditorInfoService;
    private final ItemHashTagMappingService itemHashTagMappingService;

    @Transactional(readOnly = true)
    public Item findByIdOrThrow(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.NOT_FOUND_ITEM));
    }

    @Transactional(readOnly = true)
    public ItemDetailInfoResponseDto findItemDetailInfoResponseDtoBy(Long itemId,
                                                                     ItemDetailRequestDto requestDto) {
        if (itemId == null || requestDto == null || requestDto.getBrandId() == null) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_EXISTS_REQUIRED_DATA);
        }

        ItemResponseDto itemDto = findItemResponseDtoBy(itemId, requestDto);
        List<ItemOptionResponseDto> itemOptionDtos = itemOptionService.findItemOptionResponseDtoAllBy(itemId);
        ItemEditorInfoResponseDto itemEditorInfoDto = itemEditorInfoService.findItemEditorInfoResponseDtoBy(itemId);
        List<ItemHashTagResponseDto> itemHashTagDtos = itemHashTagMappingService.findItemHashTagResponseDtoAllBy(itemId, ItemHashTagType.ITEM_DETAIL);

        return new ItemDetailInfoResponseDto(itemDto, itemOptionDtos, itemEditorInfoDto, itemHashTagDtos);
    }

    private ItemResponseDto findItemResponseDtoBy(Long itemId,
                                                  ItemDetailRequestDto requestDto) {
        ItemResponseDto itemResponseDto = itemRepository.findItemResponseDtoBy(itemId, requestDto);
        updateRelativeItemStatusBy(itemResponseDto);

        return itemResponseDto;
    }

    private void updateRelativeItemStatusBy(Object obj) {
        if (obj instanceof ItemResponseDto) {
            ItemResponseDto itemResponseDto = (ItemResponseDto) obj;
            ItemStatus status = itemResponseDto.getStatus();
            itemResponseDto.setIsDisplayItemStatus(ItemStatus.isDisplayItemStatus(status));
            itemResponseDto.setCanPurchaseItemStatus(ItemStatus.canPurchaseItemStatus(status));
        }
    }

    @Transactional(readOnly = true)
    public List<Object> findItemCountAllPerCategory() {

        return null;
    }
}
