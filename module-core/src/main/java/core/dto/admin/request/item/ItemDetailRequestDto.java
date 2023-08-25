package core.dto.admin.request.item;

import com.fasterxml.jackson.annotation.JsonFormat;
import core.domain.item.ItemDetail;
import core.domain.item.ItemOptionDetail;
import jodd.util.StringUtil;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemDetailRequestDto {
    private Long companyId;
    private Long brandId;
    private Long itemDetailId;
    private String itemNumber; // 품목 번호
    private String materialName; // 제품 소재 (면 100% 등)
    private String manufacturer; // 제조사
    private String manufacturerCountryName; // 제조국
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime manufacturingDate; // 제조연월
    private String warrantyPeriod; // 품질보증기간

    public boolean isAvailableData() {
        return this.companyId != null
                && this.brandId != null
                && this.manufacturingDate != null
                && !StringUtil.isAllBlank(
                    this.itemNumber,
                    this.materialName,
                    this.manufacturer,
                    this.manufacturerCountryName,
                    this.warrantyPeriod);
    }

    public ItemDetail toEntity(Long itemId) {
        return ItemDetail.builder()
                .itemId(itemId)
                .itemNumber(this.itemNumber)
                .materialName(this.materialName)
                .manufacturer(this.manufacturer)
                .manufacturerCountryName(this.manufacturerCountryName)
                .manufacturingDate(this.manufacturingDate)
                .warrantyPeriod(this.warrantyPeriod)
                .build();
    }
}
