package core.dto.admin.request.item;

import com.mall.choisinsa.enumeration.item.ItemOptionType;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ItemInsertStep1RequestDto {
    private Long itemCategoryId;
    private String itemNameEn;
    private String itemNameKo;
    private Long price;
    private String useTarget; // 사용 타겟층
    private int totalStockQuantity;
    private MultipartFile file;
    private List<ItemOptionRequestDto> itemOptions;
    private List<ItemThumbnailImageRequestDto> itemThumbnails;

    public boolean isAvailableData() {
        return itemCategoryId != null
                || totalStockQuantity > 0
                || !StringUtils.isBlank(itemNameKo)
                || price != null;
    }
}
