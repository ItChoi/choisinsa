package core.dto.admin.request.item;

import com.mall.choisinsa.enumeration.item.ItemEditorContentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminItemEditorContentRequestDto {
    private Long itemEditorContentId;
    private ItemEditorContentType type;
    private int displayOrder;
    private AdminItemEditorMarkupTextRequestDto markupText;
    private AdminItemEditorImageRequestDto image;

    public boolean isAvailableData() {
        return type != null
                && displayOrder > 0
                && isAvailableDataByType();
    }

    private boolean isAvailableDataByType() {
        if (type == ItemEditorContentType.IMAGE) {
            return markupText == null && image != null;
        }

        return image == null && markupText != null;
    }
}
