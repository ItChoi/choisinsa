package core.service.item;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import core.domain.item.ItemOption;
import core.domain.item.ItemOptionDetail;
import core.dto.admin.request.item.AdminItemOptionDetailRequestDto;
import core.repository.item.ItemOptionDetailRepository;
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
    public void upsertItemOptionDetails(ItemOption itemOption,
                                        Collection<AdminItemOptionDetailRequestDto> requestDtos) {
        upsertWithItemOptionDetailRequestDtos(itemOption, requestDtos);
    }

    private void upsertWithItemOptionDetailRequestDtos(ItemOption itemOption,
                                                       Collection<AdminItemOptionDetailRequestDto> requestDtos) {

        validateItemOptionDetail(itemOption, requestDtos);

        List<ItemOptionDetail> ItemOptionDetails = new ArrayList<>();
        for (AdminItemOptionDetailRequestDto requestDto : requestDtos) {
            Long itemOptionDetailId = requestDto.getItemOptionDetailId();

            if (itemOptionDetailId == null) {
                ItemOptionDetails.add(
                        ItemOptionDetail.builder()
                                .itemOptionId(itemOption.getId())
                                .name(requestDto.getName())
                                .addPrice(requestDto.getAddPrice())
                                .stockQuantity(requestDto.getStockQuantity())
                                .build());
            } else {
                ItemOptionDetail itemOptionDetail = findByIdAndItemOptionIdOrThrow(itemOptionDetailId, itemOption.getId());
                itemOptionDetail.setAddPrice(requestDto.getAddPrice());
                itemOptionDetail.setName(requestDto.getName());
                itemOptionDetail.setStockQuantity(requestDto.getStockQuantity());
            }
        }

        itemOptionDetailRepository.saveAll(ItemOptionDetails);
        itemOption.addItemOptionDetails(ItemOptionDetails);
    }

    private ItemOptionDetail findByIdAndItemOptionIdOrThrow(Long itemOptionDetailId,
                                                Long itemOptionId) {
        if (itemOptionId == null) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }
        ItemOptionDetail itemOptionDetail = findByIdOrThrow(itemOptionDetailId);
        if (!itemOptionDetail.getItemOptionId().equals(itemOptionId)) {
            throw new ErrorTypeAdviceException(ErrorType.MISMATCH_REQUEST);
        }

        return itemOptionDetail;
    }

    private ItemOptionDetail findByIdOrThrow(Long itemOptionDetailId) {
        if (itemOptionDetailId == null) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }

        return itemOptionDetailRepository.findById(itemOptionDetailId)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.NOT_FOUND_ITEM_OPTION_DETAIL));
    }

    private static void validateItemOptionDetail(ItemOption itemOption,
                                                 Collection<AdminItemOptionDetailRequestDto> requestDtos) {
        if (itemOption == null || CollectionUtils.isEmpty(requestDtos)) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_EXISTS_REQUIRED_DATA);
        }

        if (requestDtos.stream().anyMatch(dto -> !dto.isRegistrableData())) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_EXISTS_REQUIRED_DATA);
        }
    }

    public void orderByDisplayOrder(List<ItemOption> itemOptions) {
        itemOptions.forEach(itemOption -> orderByDisplayOrder(itemOption));
    }

    public void orderByDisplayOrder(ItemOption itemOption) {
        itemOption.getItemOptionDetails()
                .sort(Comparator.comparingInt(ItemOptionDetail::getDisplayOrder));
    }
}
