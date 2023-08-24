package core.dto.admin.request.item;

import com.fasterxml.jackson.annotation.JsonFormat;
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
        return companyId != null
                && brandId != null
                && manufacturingDate != null
                && !StringUtil.isAllBlank(itemNumber, materialName, manufacturer, manufacturerCountryName, warrantyPeriod);
    }
}
