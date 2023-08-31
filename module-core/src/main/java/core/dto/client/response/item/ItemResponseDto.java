package core.dto.client.response.item;

import com.mall.choisinsa.enumeration.item.ItemStatus;
import com.mall.choisinsa.enumeration.item.TargetType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@NoArgsConstructor
public class ItemResponseDto {
    @Getter
    private Long id;
    private Long itemCategoryId;
    private Long brandId;
    private ItemStatus status;
    private TargetType targetType;
    private String nameEn;
    private String nameKo;
    private Long price;
    private String fileUrl;
    private String filename;
    private int totalStockQuantity;

    @Setter
    private Boolean canPurchaseItemStatus;
    @Setter
    private Boolean isDisplayItemStatus;

    private ItemDetailResponseDto itemDetail;
    @Setter
    private List<ItemThumbnailResponseDto> itemThumbnails;

    public ItemResponseDto(Long id,
                           Long itemCategoryId,
                           Long brandId,
                           ItemStatus status,
                           TargetType targetType,
                           String nameEn,
                           String nameKo,
                           Long price,
                           String fileUrl,
                           String filename,
                           int totalStockQuantity,
                           ItemDetailResponseDto itemDetail) {
        this.id = id;
        this.itemCategoryId = itemCategoryId;
        this.brandId = brandId;
        this.status = status;
        this.targetType = targetType;
        this.nameEn = nameEn;
        this.nameKo = nameKo;
        this.price = price;
        this.fileUrl = fileUrl;
        this.filename = filename;
        this.totalStockQuantity = totalStockQuantity;
        this.itemDetail = itemDetail;
    }
}
