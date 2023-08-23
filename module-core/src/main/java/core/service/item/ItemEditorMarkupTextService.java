package core.service.item;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import core.domain.item.ItemEditorMarkupText;
import core.dto.admin.request.item.ItemEditorMarkupTextRequestDto;
import core.repository.item.ItemEditorMarkupTextRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ItemEditorMarkupTextService {

    private final ItemEditorMarkupTextRepository itemEditorMarkupTextRepository;

    @Transactional
    public void upsertItemEditorMarkupText(Long itemEditorContentId,
                                           ItemEditorMarkupTextRequestDto markupText) {
        Long itemEditorMarkupTextId = markupText.getItemEditorMarkupTextId();
        if (itemEditorMarkupTextId == null) {
            ItemEditorMarkupText.builder()
                    .itemEditorContentId(itemEditorContentId)
                    .text(markupText.getText())
                    .build();
        } else {
            ItemEditorMarkupText itemEditorMarkupText = findByIdAndItemEditorContentIdOrThrow(itemEditorMarkupTextId, itemEditorContentId);
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
