package core.dto.admin.request.item;

import com.mall.choisinsa.enumeration.item.TargetType;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class AdminItemRequestDto {
    private Long companyId;
    private Long brandId;
    private Long itemCategoryId;
    private String itemNameEn;
    private String itemNameKo;
    private Long price;
    private TargetType targetType;
    private int totalStockQuantity;
    private MultipartFile file;
    private List<AdminItemOptionRequestDto> itemOptions;
    private List<AdminItemThumbnailImageRequestDto> itemThumbnails;

    public boolean isAvailableData() {
        return companyId != null
                && brandId != null
                && itemCategoryId != null
                && totalStockQuantity > 0
                && !StringUtils.isBlank(itemNameKo)
                && price != null;
    }
}
