package core.service.item;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import core.domain.item.ItemEditorInfo;
import core.dto.admin.request.item.ItemEditorInfoRequestDto;
import core.repository.item.ItemEditorInfoRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ItemEditorInfoService {

    private final ItemEditorInfoRepository itemEditorInfoRepository;
    private final ItemEditorContentService itemEditorContentService;

    @Transactional
    public void upsertItemEditorInfo(Long itemId,
                                     Long itemEditorInfoId,
                                     ItemEditorInfoRequestDto requestDto) {

        if (BooleanUtils.isTrue(requestDto.getIsMain())) {
            clearMainItemEditorInfo(itemId);
        }

        ItemEditorInfo itemEditorInfo = insertItemEditorInfo(itemId, requestDto);

        /*if (itemEditorInfoId == null) {
            insertItemEditorInfo(step3Info);
        } else {
            updateItemEditorInfo(step3Info);
        }*/

        itemEditorContentService.upsertItemEditorContents(itemEditorInfo.getId(), requestDto.getContents());
    }

    private void clearMainItemEditorInfo(Long itemId) {
        itemEditorInfoRepository.findByItemIdAndIsMain(itemId, true)
                .ifPresent(itemEditorInfo -> itemEditorInfo.setIsMain(false));
    }

    private void updateItemEditorInfo(ItemEditorInfoRequestDto requestDto) {
        Long itemId = requestDto.getItemId();
        Long itemEditorInfoId = requestDto.getItemEditorInfoId();

        ItemEditorInfo itemEditorInfo = findByIdAndItemEditorInfoIdOrThrow(itemEditorInfoId, itemId);
        itemEditorInfo.setIsMain(requestDto.getIsMain());
        itemEditorInfo.setTitle(requestDto.getTitle());
    }

    private ItemEditorInfo findByIdAndItemEditorInfoIdOrThrow(Long itemEditorInfoId,
                                                              Long itemId) {
        ItemEditorInfo itemEditorInfo = itemEditorInfoRepository.findById(itemEditorInfoId)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.NOT_FOUND_ITEM_EDITOR_INFO));

        if (!itemEditorInfo.getItemId().equals(itemId)) {
            throw new ErrorTypeAdviceException(ErrorType.MISMATCH_REQUEST);
        }
        return itemEditorInfo;
    }

    private ItemEditorInfo insertItemEditorInfo(Long itemId,
                                      ItemEditorInfoRequestDto requestDto) {
        return itemEditorInfoRepository.save(
                ItemEditorInfo.builder()
                        .itemId(itemId)
                        .title(requestDto.getTitle())
                        .isMain(requestDto.getIsMain())
                        .build());
    }

}
