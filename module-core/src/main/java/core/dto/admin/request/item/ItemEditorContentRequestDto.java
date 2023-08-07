package core.dto.admin.request.item;

import com.mall.choisinsa.enumeration.item.ItemEditorContentType;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

@Getter
@Setter
public class ItemEditorContentRequestDto {
    private ItemEditorContentType type;
    private int displayOrder;
    private ItemEditorMarkupTextRequestDto markupText;
    private ItemEditorImageRequestDto image;

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
