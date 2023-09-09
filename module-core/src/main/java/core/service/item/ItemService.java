package core.service.item;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import com.mall.choisinsa.enumeration.hashtag.ItemHashTagType;
import com.mall.choisinsa.enumeration.item.ItemStatus;
import core.domain.item.Item;
import core.domain.item.ItemCategory;
import core.dto.client.request.item.ItemDetailRequestDto;
import core.dto.client.response.item.*;
import core.repository.item.ItemRepository;
import core.service.hashtag.ItemHashTagMappingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemOptionService itemOptionService;
    private final ItemEditorInfoService itemEditorInfoService;
    private final ItemHashTagMappingService itemHashTagMappingService;
    private final ItemCategoryService itemCategoryService;

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

    @Transactional(readOnly = true)
    public List<ItemCountAllPerCategoryApplicationReadyDto> findItemCountAllPerCategoryApplicationReadyDtoAll() {
        List<ItemCategory> topItemCategories = itemCategoryService.findALlByParentIdIsNullOrderByDisplayOrderAsc();

        List<ItemCountAllPerCategoryApplicationReadyDto> responseDtos = convertToItemCategoryApplicationReadyDtoAndSort(topItemCategories);
        calculateItemCountAllPerCategory(responseDtos);

        return responseDtos;
    }

    private void calculateItemCountAllPerCategory(Collection<ItemCountAllPerCategoryApplicationReadyDto> itemCategoryDtos) {
        // TODO LIST
    }

    private List<ItemCountAllPerCategoryApplicationReadyDto> convertToItemCategoryApplicationReadyDtoAndSort(Collection<ItemCategory> topItemCategories) {
        if (CollectionUtils.isEmpty(topItemCategories)) {
            return Collections.emptyList();
        }

        return topItemCategories.stream()
                .map(ItemCountAllPerCategoryApplicationReadyDto::new)
                .collect(Collectors.toList());
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
}

































