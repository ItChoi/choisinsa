package core.service.item;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import core.domain.item.ItemOption;
import core.domain.item.ItemOptionDetail;
import core.dto.admin.request.item.ItemOptionDetailRequestDto;
import core.repository.item.ItemOptionDetailRepository;
import io.reactivex.rxjava3.internal.operators.single.SingleDoAfterTerminate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@RequiredArgsConstructor
@Service
public class ItemOptionDetailService {

    private final ItemOptionDetailRepository itemOptionDetailRepository;

    @Transactional
    public void saveItemOptionDetail(ItemOption itemOption,
                                     Collection<ItemOptionDetailRequestDto> requestDtos) {
        validateItemOptionDetail(itemOption, requestDtos);

        Set<ItemOptionDetail> addedOptionDetails = new HashSet<>();
        for (ItemOptionDetailRequestDto requestDto : requestDtos) {
            addedOptionDetails.add(saveWithItemOptionDetailRequestDto(requestDto, itemOption));
        }

        itemOption.getItemOptionDetails().addAll(addedOptionDetails);
    }

    private ItemOptionDetail saveWithItemOptionDetailRequestDto(ItemOptionDetailRequestDto requestDto,
                                                                ItemOption itemOption) {
        if (!requestDto.isRegistrableData()) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_EXISTS_REQUIRED_DATA);
        }

        return itemOptionDetailRepository.save(
                ItemOptionDetail.builder()
                        .itemOptionId(itemOption.getId())
                        .name(requestDto.getName())
                        .addPrice(requestDto.getAddPrice())
                        .stockQuantity(requestDto.getStockQuantity())
                        .build()
        );
    }

    private static void validateItemOptionDetail(ItemOption itemOption,
                                                 Collection<ItemOptionDetailRequestDto> requestDtos) {
        if (itemOption == null || CollectionUtils.isEmpty(requestDtos)) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_EXISTS_REQUIRED_DATA);
        }
    }
}
