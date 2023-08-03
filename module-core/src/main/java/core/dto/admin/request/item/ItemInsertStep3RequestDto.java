package core.dto.admin.request.item;

import com.mall.choisinsa.enumeration.item.ItemEditorContentType;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ItemInsertStep3RequestDto {
    private Long itemId;
    private String editInfoTitle; // 상품 에디터 제목
    private Boolean isMain; // 에디터 메인 지정 여부

    public class ItemEditorContentRequestDto {
        private ItemEditorContentType type;
        private int displayOrder;

        public class ItemEditorMarkupTextRequestDto {
            private String text;
        }

        public class ItemEditorImageRequestDto {
            private MultipartFile file;
        }
    }

    public boolean isAvailableData() {
        return itemId == null
                || StringUtils.isBlank(editInfoTitle)
                || isMain == null;
    }
}