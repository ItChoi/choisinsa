package core.service.item;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import core.domain.item.Item;
import core.domain.item.ItemOption;
import core.domain.item.ItemOptionDetail;
import core.dto.admin.request.item.ItemOptionRequestDto;
import core.mapper.item.ItemOptionMapper;
import core.repository.item.ItemOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemOptionService {

    private final ItemOptionRepository itemOptionRepository;
    private final ItemOptionDetailService itemOptionDetailService;

    @Transactional
    public void upsertItemOptions(Item item,
                                  Collection<ItemOptionRequestDto> requestDtos) {
        validateItemOptions(item, requestDtos);
        for (ItemOptionRequestDto requestDto : requestDtos) {
            upsertWithItemOption(item, requestDto);
        }

        validateAfterRegister(item);
    }

    private void upsertWithItemOption(Item item,
                                            ItemOptionRequestDto requestDto) {
        if (!requestDto.isRegistrableData()) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_EXISTS_REQUIRED_DATA);
        }

        Long itemOptionId = requestDto.getItemOptionId();
        if (itemOptionId == null) {
            insertItemOption(item, requestDto);
        } else {
            updateItemOption(item, requestDto);
        }
    }

    private ItemOption insertItemOption(Item item,
                                        ItemOptionRequestDto requestDto) {

        ItemOption itemOption = itemOptionRepository.save(
                ItemOption.builder()
                        .itemId(item.getId())
                        .itemOptionType(requestDto.getItemOptionType())
                        .displayOrder(requestDto.getDisplayOrder())
                        .build());
        itemOptionDetailService.saveItemOptionDetail(itemOption, requestDto.getItemOptionDetails());

        return itemOption;
    }

    private ItemOption updateItemOption(Item item,
                                        ItemOptionRequestDto requestDto) {
        Long itemOptionId = requestDto.getItemOptionId();
        ItemOption itemOption = findByIdAndItemIdOrThrow(itemOptionId, item.getId());

        itemOption.setItemOptionType(requestDto.getItemOptionType());
        itemOption.setDisplayOrder(requestDto.getDisplayOrder());

        itemOptionDetailService.saveItemOptionDetail(itemOption, requestDto.getItemOptionDetails());

        return itemOption;
    }

    @Transactional(readOnly = true)
    public ItemOption findByIdOrThrow(Long itemOptionId) {
        return itemOptionRepository.findById(itemOptionId)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.NOT_FOUND_ITEM_OPTION));
    }

    @Transactional(readOnly = true)
    public ItemOption findByIdAndItemIdOrThrow(Long itemOptionId,
                                               Long itemId) {
        if (itemOptionId == null || itemId == null) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }

        ItemOption itemOption = findByIdOrThrow(itemOptionId);
        if (!itemOption.getItemId().equals(itemId)) {
            throw new ErrorTypeAdviceException(ErrorType.MISMATCH_REQUEST);
        }
        return itemOptionRepository.findById(itemOptionId)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.NOT_FOUND_ITEM_OPTION));
    }

    private void validateItemOptions(Item item,
                                     Collection<ItemOptionRequestDto> requestDtos) {
        if (item == null || CollectionUtils.isEmpty(requestDtos)) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }
    }

    @Transactional(readOnly = true)
    public void validateAfterRegister(Item item) {
        int itemOptionTotalQuantity = findAllByItemId(item.getId()).stream()
                .map(itemOption -> itemOption.getItemOptionDetails())
                .flatMap(Collection::stream)
                .mapToInt(ItemOptionDetail::getStockQuantity)
                .sum();

        if (item.getTotalStockQuantity() < itemOptionTotalQuantity) {
            throw new ErrorTypeAdviceException(ErrorType.CAN_NOT_EXCEED_ITEM_TOTAL_QUANTITY_WITH_OPTION_ITEM_TOTAL_QUANTITY);
        }
    }

    @Transactional(readOnly = true)
    public List<ItemOption> findAllByItemId(Long itemId) {
        return itemOptionRepository.findAllByItemId(itemId);
    }
}
