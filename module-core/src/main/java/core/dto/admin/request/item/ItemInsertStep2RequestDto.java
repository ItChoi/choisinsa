package core.dto.admin.request.item;

import jodd.util.StringUtil;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ItemInsertStep2RequestDto {
    private Long itemId;
    private String itemNumber; // 품목 번호
    private String materialName; // 제품 소재 (면 100% 등)
    private String manufacturer; // 제조사
    private String manufacturerCountryName; // 제조국
    private LocalDateTime manufacturingDate; // 제조연월
    private String warrantyPeriod; // 품질보증기간

    public boolean isAvailableData() {
        return itemId == null
                || manufacturingDate == null
                || StringUtil.isAllBlank(itemNumber, materialName, manufacturer, manufacturerCountryName, warrantyPeriod);
    }
}
