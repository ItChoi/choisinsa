package core.service.item;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import core.domain.item.Item;
import core.dto.client.request.item.ItemDetailRequestDto;
import core.dto.client.response.item.ItemDetailInfoResponseDto;
import core.repository.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ItemService {

    private ItemRepository itemRepository;

    @Transactional(readOnly = true)
    public Item findByIdOrThrow(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.NOT_FOUND_ITEM));
    }

    @Transactional(readOnly = true)
    public ItemDetailInfoResponseDto getItemDetail(Long memberId,
                                                   Long itemId,
                                                   ItemDetailRequestDto requestDto) {

        Item item = findByIdOrThrow(itemId);
        boolean canPurchaseItemStatus = item.canPurchaseItemStatus();




        return null;
    }
}
