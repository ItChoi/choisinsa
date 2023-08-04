package core.service.item;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import core.domain.item.ItemDetail;
import core.dto.admin.request.item.ItemInsertStep2RequestDto;
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
    public void saveItemDetail(ItemInsertStep2RequestDto step2Info) {
        validateItem(step2Info);
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

    private void validateItem(ItemInsertStep2RequestDto step2Info) {
        if (!step2Info.isAvailableData()) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }

        if (!itemRepository.existsById(step2Info.getItemId())) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }
    }
}
