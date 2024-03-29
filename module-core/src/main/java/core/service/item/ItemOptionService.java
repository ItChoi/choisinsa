package core.service.item;

import core.common.exception.ErrorTypeAdviceException;
import core.common.exception.ErrorType;
import core.domain.item.Item;
import core.domain.item.ItemOption;
import core.domain.item.ItemOptionDetail;
import core.dto.admin.request.item.AdminItemOptionRequestDto;
import core.dto.client.response.item.ItemOptionResponseDto;
import core.repository.item.ItemOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemOptionService {

    private final ItemOptionRepository itemOptionRepository;
    private final ItemOptionDetailService itemOptionDetailService;

    @Transactional
    public void upsertItemOptions(Item item,
                                  Collection<AdminItemOptionRequestDto> requestDtos) {
        validateItemOptions(item, requestDtos);

        for (AdminItemOptionRequestDto requestDto : requestDtos) {
            Long itemOptionId = requestDto.getItemOptionId();
            if (itemOptionId == null) {
                insertItemOption(item, requestDto);
            } else {
                updateItemOption(item, requestDto);
            }

        }
        validateAfterRegister(item);
    }

    private void insertItemOption(Item item,
                                  AdminItemOptionRequestDto requestDto) {
        ItemOption itemOption = itemOptionRepository.save(
                ItemOption.builder()
                        .itemId(item.getId())
                        .itemOptionType(requestDto.getItemOptionType())
                        .displayOrder(requestDto.getDisplayOrder())
                        .build());

        itemOptionDetailService.upsertItemOptionDetails(itemOption, requestDto.getItemOptionDetails());
    }

    private void updateItemOption(Item item,
                                  AdminItemOptionRequestDto requestDto) {
        ItemOption itemOption = findByIdAndItemIdOrThrow(requestDto.getItemOptionId(), item.getId());

        if (itemOption.getItemOptionType() != requestDto.getItemOptionType()) {
            // TODO 옵션 타입이 변경되는 경우, 하위 데이터들을 어떻게 처리할지 정책 필요
            itemOption.setItemOptionType(requestDto.getItemOptionType());
        }
        itemOption.setDisplayOrder(requestDto.getDisplayOrder());

        itemOptionDetailService.upsertItemOptionDetails(itemOption, requestDto.getItemOptionDetails());
    }


    @Transactional(readOnly = true)
    public ItemOption findByIdOrThrow(Long itemOptionId) {
        if (itemOptionId == null) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }

        return itemOptionRepository.findById(itemOptionId)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.NOT_FOUND_ITEM_OPTION));
    }

    @Transactional(readOnly = true)
    public ItemOption findByIdAndItemIdOrThrow(Long itemOptionId,
                                               Long itemId) {
        if (itemId == null) {
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
                                     Collection<AdminItemOptionRequestDto> requestDtos) {
        if (item == null || CollectionUtils.isEmpty(requestDtos)) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }

        if (requestDtos.stream().anyMatch(dto -> !dto.isRegistrableData())) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_EXISTS_REQUIRED_DATA);
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
    public List<ItemOption> findOrderedAllByItemId(Long itemId) {
        List<ItemOption> itemOptions = findAllByItemId(itemId);
        orderByDisplayOrder(itemOptions);

        return itemOptions;
    }

    private void orderByDisplayOrder(List<ItemOption> itemOptions) {
        if (CollectionUtils.isEmpty(itemOptions)) {
            return;
        }
        itemOptions.sort(Comparator.comparingInt(ItemOption::getDisplayOrder));
        itemOptionDetailService.orderByDisplayOrder(itemOptions);
    }

    @Transactional(readOnly = true)
    public List<ItemOption> findAllByItemId(Long itemId) {
        return itemOptionRepository.findAllByItemId(itemId);
    }

    @Transactional(readOnly = true)
    public List<ItemOptionResponseDto> findItemOptionResponseDtoAllBy(Long itemId) {
        if (itemId == null) {
            return Collections.emptyList();
        }

        /*List<ItemOptionResponseDto> itemOptionDtos = findOrderedAllByItemId(itemId).stream()
                .map(ItemOptionMapper.INSTANCE::toItemOptionResponseDto)
                .collect(Collectors.toList());

        return itemOptionDtos;*/

        return null;
    }
}
