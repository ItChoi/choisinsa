package core.dto.admin.request.item;

import com.mall.choisinsa.enumeration.item.ItemEditorContentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminItemEditorContentRequestDto {
    private Long id;
    private ItemEditorContentType type;
    private int displayOrder;
    private AdminItemEditorMarkupTextRequestDto itemEditorMarkupText;
    private AdminItemEditorImageRequestDto itemEditorImage;

    public boolean isAvailableData() {
        return type != null
                && displayOrder > 0
                && isAvailableDataByType();
    }

    private boolean isAvailableDataByType() {
        if (type == ItemEditorContentType.IMAGE) {
            return itemEditorMarkupText == null && itemEditorImage != null;
        }

        return itemEditorImage == null && itemEditorMarkupText != null;
    }
}
