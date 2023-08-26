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
    public void putItemDetail(Long itemId,
                              ItemDetailRequestDto requestDto) {
        validateItem(itemId, requestDto);

        Long itemDetailId = requestDto.getItemDetailId();
        if (itemDetailId == null) {
            insertItemDetail(itemId, requestDto);
        } else {
            updateItemDetail(itemId, itemDetailId, requestDto);
        }
    }

    private void updateItemDetail(Long itemId,
                                  Long itemDetailId,
                                  ItemDetailRequestDto requestDto) {
        if (itemId == null || itemDetailId == null) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_EXISTS_REQUIRED_DATA);
        }

        ItemDetail itemDetail = findByIdAndItemIdOrThrow(itemDetailId, itemId);
        ItemMapper.INSTANCE.updateItemDetail(itemDetail, requestDto);
    }

    private ItemDetail findByIdAndItemIdOrThrow(Long itemDetailId,
                                                Long itemId) {
        ItemDetail itemDetail = findByIdOrThrow(itemDetailId);
        if (!itemDetail.getItemId().equals(itemId)) {
            throw new ErrorTypeAdviceException(ErrorType.MISMATCH_REQUEST);
        }

        return itemDetail;
    }

    private ItemDetail findByIdOrThrow(Long itemDetailId) {
        return itemDetailRepository.findById(itemDetailId)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.NOT_FOUND_ITEM_DETAIL));
    }

    private void insertItemDetail(Long itemId,
                                  ItemDetailRequestDto requestDto) {
        itemDetailRepository.save(requestDto.toEntity(itemId));
    }

    private void validateItem(Long itemId,
                              ItemDetailRequestDto requestDto) {
        if (!requestDto.isAvailableData()) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }

        if (!itemRepository.existsById(itemId)) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }
    }
}
