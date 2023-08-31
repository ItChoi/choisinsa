package core.dto.client.response.item;

import com.mall.choisinsa.enumeration.item.ItemEditorContentType;
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
