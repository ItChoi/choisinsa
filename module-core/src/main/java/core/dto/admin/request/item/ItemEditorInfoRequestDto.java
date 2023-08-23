package core.dto.admin.request.item;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Getter
public class ItemEditorInfoRequestDto {
    private Long itemEditorInfoId;
    private Long itemId;
    private String title; // 상품 에디터 제목
    private Boolean isMain; // 에디터 메인 지정 여부
    private List<ItemEditorContentRequestDto> contents;

    public boolean isAvailableData() {
        return itemId != null
                && !StringUtils.isBlank(title)
                && isMain != null
                && !CollectionUtils.isEmpty(contents);
    }
}