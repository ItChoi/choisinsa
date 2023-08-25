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

    private void clearMainItemEditorInfo(Long itemId) {
        itemEditorInfoRepository.findByItemIdAndIsMain(itemId, true)
                .ifPresent(itemEditorInfo -> itemEditorInfo.setIsMain(false));
    }

    @Transactional
    public void updateItemEditorInfo(Long itemId,
                                      Long itemEditorInfoId,
                                      ItemEditorInfoRequestDto requestDto) {
        if (BooleanUtils.isTrue(requestDto.getIsMain())) {
            clearMainItemEditorInfo(itemId);
        }

        ItemEditorInfo itemEditorInfo = findByIdAndItemEditorInfoIdOrThrow(itemEditorInfoId, itemId);
        itemEditorInfo.setIsMain(requestDto.getIsMain());
        itemEditorInfo.setTitle(requestDto.getTitle());

        itemEditorContentService.upsertItemEditorContents(itemEditorInfo.getId(), requestDto.getContents());
    }

    @Transactional
    public void insertItemEditorInfo(Long itemId,
                                     ItemEditorInfoRequestDto requestDto) {
        if (BooleanUtils.isTrue(requestDto.getIsMain())) {
            clearMainItemEditorInfo(itemId);
        }

        ItemEditorInfo itemEditorInfo = itemEditorInfoRepository.save(ItemEditorInfo.builder()
                .itemId(itemId)
                .title(requestDto.getTitle())
                .isMain(requestDto.getIsMain())
                .build());

        itemEditorContentService.upsertItemEditorContents(itemEditorInfo.getId(), requestDto.getContents());
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
}
