package core.service.item;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import core.domain.item.ItemEditorContent;
import core.domain.item.ItemEditorInfo;
import core.dto.admin.request.item.AdminItemEditorContentRequestDto;
import core.repository.item.ItemEditorContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemEditorContentService {

    private final ItemEditorContentRepository itemEditorContentRepository;
    private final ItemEditorMarkupTextService itemEditorMarkupTextService;
    private final ItemEditorImageService itemEditorImageService;

    @Transactional
    public void upsertItemEditorContents(Long itemEditorInfoId,
                                         Collection<AdminItemEditorContentRequestDto> contents) {
        for (AdminItemEditorContentRequestDto content : contents) {
            ItemEditorContent itemEditorContent = upsertItemEditorContent(itemEditorInfoId, content);

            switch (content.getType()) {
                case MARKUP_TEXT:
                    itemEditorMarkupTextService.upsertItemEditorMarkupText(itemEditorContent, content.getMarkupText());
                    break;
                case IMAGE:
                    itemEditorImageService.upsertItemEditorImage(itemEditorContent.getId(), content.getImage());
                    break;
                default:
                    throw new ErrorTypeAdviceException(ErrorType.NOT_EXISTS_REQUIRED_DATA);
            }
        }
    }

    private ItemEditorContent upsertItemEditorContent(Long itemEditorInfoId,
                                                      AdminItemEditorContentRequestDto content) {

        Long itemEditorContentId = content.getItemEditorContentId();
        if (itemEditorContentId == null) {
            return itemEditorContentRepository.save(ItemEditorContent.builder()
                    .itemEditorInfoId(itemEditorInfoId)
                    .type(content.getType())
                    .displayOrder(content.getDisplayOrder())
                    .build());
        } else {
            ItemEditorContent itemEditorContent = findByIdAndItemEditorInfoIdOrThrow(itemEditorContentId, itemEditorInfoId);
            if (itemEditorContent.getType() != content.getType()) {
                throw new ErrorTypeAdviceException(ErrorType.CAN_NOT_CHANGE_CONTENT_TYPE);
            }
            itemEditorContent.setDisplayOrder(content.getDisplayOrder());

            return itemEditorContent;
        }
    }

    private ItemEditorContent findByIdAndItemEditorInfoIdOrThrow(Long itemEditorContentId,
                                                                 Long itemEditorInfoId) {
        ItemEditorContent itemEditorContent = itemEditorContentRepository.findById(itemEditorContentId)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.NOT_FOUND_ITEM_EDITOR_CONTENT));

        if (!itemEditorContent.getItemEditorInfoId().equals(itemEditorInfoId)) {
            throw new ErrorTypeAdviceException(ErrorType.MISMATCH_REQUEST);
        }

        return itemEditorContent;
    }

    @Transactional
    public void deleteItemEditorContentByItemEditorInfoIdAndIdIn(Long itemEditorInfoId,
                                                                 Collection<Long> itemEditorContentIds) {
        if (itemEditorInfoId == null || CollectionUtils.isEmpty(itemEditorContentIds)) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_EXISTS_REQUIRED_DATA);
        }

        validateItemEditorContentForDelete(itemEditorInfoId, itemEditorContentIds);
        itemEditorContentRepository.deleteByIdIn(itemEditorContentIds);
    }

    private List<ItemEditorContent> findByItemEditorInfoIdAndIdIn(Long itemEditorInfoId,
                                                               Collection<Long> itemEditorContentIds) {

        return itemEditorContentRepository.findByItemEditorInfoIdAndIdIn(itemEditorInfoId, itemEditorContentIds);
    }

    private void validateItemEditorContentForDelete(Long itemEditorInfoId,
                                                    Collection<Long> itemEditorContentIds) {

        List<ItemEditorContent> itemEditorContents = findByItemEditorInfoIdAndIdIn(itemEditorInfoId, itemEditorContentIds);
        if (itemEditorContents.size() != itemEditorContentIds.size()) {
            throw new ErrorTypeAdviceException(ErrorType.MISMATCH_REQUEST);
        }
    }

    public void orderByDisplayOrder(ItemEditorInfo itemEditorInfo) {
        if (itemEditorInfo == null) {
            return;
        }

        itemEditorInfo.getItemEditorContents().sort(Comparator.comparingInt(ItemEditorContent::getDisplayOrder));
    }
}
