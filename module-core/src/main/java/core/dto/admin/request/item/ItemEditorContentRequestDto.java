package core.dto.admin.request.item;

import com.mall.choisinsa.enumeration.item.ItemEditorContentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemEditorContentRequestDto {
    private ItemEditorContentType type;
    private int displayOrder;
    private ItemEditorMarkupTextRequestDto markupText;
    private ItemEditorImageRequestDto image;
}
