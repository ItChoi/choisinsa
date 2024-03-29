package core.service.item;

import core.common.exception.ErrorTypeAdviceException;
import core.common.exception.ErrorType;
import core.domain.item.ItemEditorContent;
import core.domain.item.ItemEditorMarkupText;
import core.dto.admin.request.item.AdminItemEditorMarkupTextRequestDto;
import core.repository.item.ItemEditorMarkupTextRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ItemEditorMarkupTextService {

    private final ItemEditorMarkupTextRepository itemEditorMarkupTextRepository;

    @Transactional
    public void upsertItemEditorMarkupText(ItemEditorContent itemEditorContent,
                                           AdminItemEditorMarkupTextRequestDto markupText) {
        Long itemEditorMarkupTextId = markupText.getId();
        if (itemEditorMarkupTextId == null) {
            itemEditorMarkupTextRepository.save(ItemEditorMarkupText.builder()
                    .itemEditorContentId(itemEditorContent.getId())
                    .text(markupText.getText())
                    .build());
        } else {
            ItemEditorMarkupText itemEditorMarkupText = findByIdAndItemEditorContentIdOrThrow(itemEditorMarkupTextId, itemEditorContent.getId());
            itemEditorMarkupText.setText(markupText.getText());
        }
    }

    private ItemEditorMarkupText findByIdAndItemEditorContentIdOrThrow(Long itemEditorMarkupTextId,
                                                                       Long itemEditorContentId) {
        ItemEditorMarkupText itemEditorMarkupText = itemEditorMarkupTextRepository.findById(itemEditorMarkupTextId)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.NOT_FOUND_ITEM_EDITOR_MARKUP_TEXT));

        if (!itemEditorMarkupText.getItemEditorContentId().equals(itemEditorContentId)) {
            throw new ErrorTypeAdviceException(ErrorType.MISMATCH_REQUEST);
        }

        return itemEditorMarkupText;
    }
}
