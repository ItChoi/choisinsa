package core.service.item;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import core.domain.item.Item;
import core.domain.item.ItemOption;
import core.domain.item.ItemOptionDetail;
import core.dto.admin.request.item.ItemOptionDetailRequestDto;
import core.dto.admin.request.item.ItemOptionRequestDto;
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
    public void saveItemOptions(Item item,
                                Collection<ItemOptionRequestDto> requestDtos) {
        validateItemOptions(item, requestDtos);
        // TODO: 옵션 등록, item_option -> 존재하면 그거 쓰고, 존재하지 않는 경우 등
        for (ItemOptionRequestDto requestDto : requestDtos) {
            ItemOption itemOption = saveWithItemOptionRequestDto(item, requestDto);
            itemOptionDetailService.saveItemOptionDetail(itemOption, requestDto.getItemOptionDetails());
        }

        validateAfterRegister(item);
    }

    private ItemOption saveWithItemOptionRequestDto(Item item,
                                                    ItemOptionRequestDto requestDto) {
        if (requestDto.isRegistrableData()) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_EXISTS_REQUIRED_DATA);
        }

        return itemOptionRepository.save(
                ItemOption.builder()
                        .itemId(item.getId())
                        .itemOptionType(requestDto.getItemOptionType())
                        .displayOrder(requestDto.getDisplayOrder())
                        .build()
        );
    }

    private void validateItemOptions(Item item,
                                     Collection<ItemOptionRequestDto> requestDtos) {
        if (item == null || CollectionUtils.isEmpty(requestDtos)) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }

        /*int quantityOfItemOptionTotalQuantity = requestDtos.stream()
                .map(option -> option.getItemOptionDetails())
                .flatMap(Collection::stream)
                .mapToInt(ItemOptionDetailRequestDto::getStockQuantity)
                .sum();

        if (item.getTotalStockQuantity() < quantityOfItemOptionTotalQuantity) {
            throw new ErrorTypeAdviceException(ErrorType.CAN_NOT_EXCEED_ITEM_TOTAL_QUANTITY_WITH_OPTION_ITEM_TOTAL_QUANTITY);
        }*/
    }

    @Transactional(readOnly = true)
    public void validateAfterRegister(Item item) {
        int quantityOfItemOptionTotalQuantity = findAllByItemId(item.getId()).stream()
                .map(itemOption -> itemOption.getItemOptionDetails())
                .flatMap(Collection::stream)
                .mapToInt(ItemOptionDetail::getStockQuantity)
                .sum();

        if (item.getTotalStockQuantity() < quantityOfItemOptionTotalQuantity) {
            throw new ErrorTypeAdviceException(ErrorType.CAN_NOT_EXCEED_ITEM_TOTAL_QUANTITY_WITH_OPTION_ITEM_TOTAL_QUANTITY);
        }
    }

    @Transactional(readOnly = true)
    public List<ItemOption> findAllByItemId(Long itemId) {
        return itemOptionRepository.findAllByItemId(itemId);
    }
}
