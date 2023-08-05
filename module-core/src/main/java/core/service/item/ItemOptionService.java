package core.service.item;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import core.domain.item.Item;
import core.domain.item.ItemOption;
import core.dto.admin.request.item.ItemOptionDetailRequestDto;
import core.dto.admin.request.item.ItemOptionRequestDto;
import core.repository.item.ItemOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemOptionService {

    private final ItemOptionRepository itemOptionRepository;

    @Transactional
    public void saveItemOptions(Item item,
                                List<ItemOptionRequestDto> itemOptions) {
        validateItemOptions(item, itemOptions);
        // TODO: 옵션 등록, item_option -> 존재하면 그거 쓰고, 존재하지 않는 경우 등
        List<ItemOption> saveTargetItemOptions = new ArrayList<>();
        for (ItemOptionRequestDto itemOption : itemOptions) {
            //itemOption
        }


    }

    private void validateItemOptions(Item item,
                                     Collection<ItemOptionRequestDto> itemOptions) {
        if (item == null || CollectionUtils.isEmpty(itemOptions)) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }

        int quantityOfItemOptionTotalQuantity = itemOptions.stream()
                .map(option -> option.getItemOptionDetails())
                .flatMap(List::stream)
                .mapToInt(ItemOptionDetailRequestDto::getStockQuantity)
                .sum();
        if (item.getTotalStockQuantity() < quantityOfItemOptionTotalQuantity) {
            throw new ErrorTypeAdviceException(ErrorType.CAN_NOT_EXCEED_ITEM_TOTAL_QUANTITY_WITH_OPTION_ITEM_TOTAL_QUANTITY);
        }
    }
}
