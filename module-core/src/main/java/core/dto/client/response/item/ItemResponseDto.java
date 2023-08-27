package core.dto.client.response.item;

import com.mall.choisinsa.enumeration.item.ItemStatus;
import com.mall.choisinsa.enumeration.item.TargetType;

import java.util.List;

public class ItemResponseDto {
    private Long itemId;
    private Long itemCategoryId;
    private Long brandId;
    private ItemStatus status;
    private TargetType targetType;
    private String nameEn;
    private String nameKn;
    private Long price;
    private String fileUrl;
    private String filename;
    private int totalStockQuantity;


    private ItemDetailResponseDto itemDetail;
    private List<ItemThumbnailResponseDto> itemThumbnails;
    private ItemEditorInfoResponseDto itemEditorInfo;
    private List<ItemHashTagResponseDto> itemHashTags;
}
