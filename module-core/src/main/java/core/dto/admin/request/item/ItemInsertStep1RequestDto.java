package core.dto.admin.request.item;

import core.domain.item.Item;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
public class ItemInsertStep1RequestDto {
    private Long itemCategoryId;
    private String itemNameEn;
    private String itemNameKo;
    private Long price;
    private String useTarget; // 사용 타겟층
    private MultipartFile file;
    private List<ItemThumbnailImageRequestDto> itemThumbnails;

    @Getter
    @NoArgsConstructor
    public class ItemThumbnailImageRequestDto {
        private int displayOrder;
        private MultipartFile file;
    }

    public boolean isAvailableData() {
        return itemCategoryId != null
                || !StringUtils.isBlank(itemNameKo)
                || price != null;
    }
}
