package core.service.item;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import core.domain.item.ItemDetail;
import core.dto.admin.request.item.ItemDetailRequestDto;
import core.mapper.item.ItemMapper;
import core.repository.item.ItemDetailRepository;
import core.repository.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ItemDetailService {

    private final ItemDetailRepository itemDetailRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public void upsertItemDetail(ItemDetailRequestDto step2Info) {
        validateItem(step2Info);

        Long itemDetailId = step2Info.getItemDetailId();
        if (itemDetailId == null) {
            insertItemDetail(step2Info);
        } else {
            ItemDetail itemDetail = findByIdAndItemIdOrThrow(itemDetailId, step2Info.getItemId());
            ItemMapper.INSTANCE.updateItemDetail(itemDetail, step2Info);
        }
    }

    private ItemDetail findByIdAndItemIdOrThrow(Long itemDetailId,
                                                Long itemId) {
        ItemDetail itemDetail = findByIdOrThrow(itemDetailId);
        if (itemDetail.getItemId().equals(itemId)) {
            throw new ErrorTypeAdviceException(ErrorType.MISMATCH_REQUEST);
        }

        return itemDetail;
    }

    private ItemDetail findByIdOrThrow(Long itemDetailId) {
        return itemDetailRepository.findById(itemDetailId)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.NOT_FOUND_ITEM_DETAIL));
    }

    private void insertItemDetail(ItemDetailRequestDto step2Info) {
        itemDetailRepository.save(
                ItemDetail.builder()
                        .itemId(step2Info.getItemId())
                        .itemNumber(step2Info.getItemNumber())
                        .materialName(step2Info.getMaterialName())
                        .manufacturer(step2Info.getManufacturer())
                        .manufacturerCountryName(step2Info.getManufacturerCountryName())
                        .manufacturingDate(step2Info.getManufacturingDate())
                        .warrantyPeriod(step2Info.getWarrantyPeriod())
                        .build()
        );
    }

    private void validateItem(ItemDetailRequestDto step2Info) {
        if (!step2Info.isAvailableData()) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }

        if (!itemRepository.existsById(step2Info.getItemId())) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }
    }
}
