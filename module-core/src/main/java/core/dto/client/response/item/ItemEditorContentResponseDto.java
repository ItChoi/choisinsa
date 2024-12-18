package core.dto.client.response.item;

import core.common.enumeration.item.ItemEditorContentType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemEditorContentResponseDto {
    private Long id;
    private Long itemEditorInfoId;
    private ItemEditorContentType type;
    private int displayOrder;
    private ItemEditorImageResponseDto itemEditorImage;
    private ItemEditorMarkupTextResponseDto itemEditorMarkupText;
}
