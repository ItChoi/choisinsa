package core.service.item;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import core.domain.item.ItemEditorInfo;
import core.dto.admin.request.item.ItemEditorInfoRequestDto;
import core.repository.item.ItemEditorInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ItemEditorInfoService {

    private final ItemEditorInfoRepository itemEditorInfoRepository;
    private final ItemEditorContentService itemEditorContentService;

    @Transactional
    public void upsertItemEditorInfo(ItemEditorInfoRequestDto step3Info) {
        Long itemEditorInfoId = step3Info.getItemEditorInfoId();
        Long itemId = step3Info.getItemId();

        Boolean isMain = step3Info.getIsMain();
        if (isMain) {
            itemEditorInfoRepository.findByItemIdAndIsMain(itemId, isMain)
                    .ifPresent(itemEditorInfo -> itemEditorInfo.setIsMain(false));
        }

        if (itemEditorInfoId == null) {
            insertItemEditorInfo(step3Info);
        } else {
            updateItemEditorInfo(step3Info);
        }

        itemEditorContentService.upsertItemEditorContents(step3Info.getItemId(), itemEditorInfoId, step3Info.getContents());
    }

    private void updateItemEditorInfo(ItemEditorInfoRequestDto step3Info) {
        Long itemId = step3Info.getItemId();
        Long itemEditorInfoId = step3Info.getItemEditorInfoId();

        ItemEditorInfo itemEditorInfo = findByIdAndItemEditorInfoIdOrThrow(itemEditorInfoId, itemId);
        itemEditorInfo.setIsMain(step3Info.getIsMain());
        itemEditorInfo.setTitle(step3Info.getTitle());
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

    private void insertItemEditorInfo(ItemEditorInfoRequestDto step3Info) {
        itemEditorInfoRepository.save(
                ItemEditorInfo.builder()
                        .itemId(step3Info.getItemId())
                        .title(step3Info.getTitle())
                        .isMain(step3Info.getIsMain())
                        .build());
    }
}
