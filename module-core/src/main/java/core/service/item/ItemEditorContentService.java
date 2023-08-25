package core.service.item;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import core.domain.item.ItemEditorContent;
import core.dto.admin.request.item.ItemEditorContentRequestDto;
import core.repository.item.ItemEditorContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@RequiredArgsConstructor
@Service
public class ItemEditorContentService {

    private final ItemEditorContentRepository itemEditorContentRepository;
    private final ItemEditorMarkupTextService itemEditorMarkupTextService;
    private final ItemEditorImageService itemEditorImageService;

    @Transactional
    public void upsertItemEditorContents(Long itemEditorInfoId,
                                         Collection<ItemEditorContentRequestDto> contents) {
        for (ItemEditorContentRequestDto content : contents) {
            ItemEditorContent itemEditorContent = upsertItemEditorContent(itemEditorInfoId, content);

            switch (content.getType()) {
                case MARKUP_TEXT:
                    itemEditorMarkupTextService.upsertItemEditorMarkupText(itemEditorContent.getId(), content.getMarkupText());
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
                                                      ItemEditorContentRequestDto content) {

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
}
