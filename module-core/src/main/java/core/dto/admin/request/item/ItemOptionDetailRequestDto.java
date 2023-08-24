package core.dto.admin.request.item;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Setter
@Getter
public class ItemOptionDetailRequestDto {
    private Long itemOptionDetailId;
    private String name;
    private Long addPrice;
    private int stockQuantity;

    public boolean isRegistrableData() {
        return StringUtils.hasText(name)
                && addPrice != null
                && addPrice >= 0
                && stockQuantity > 0;
    }
}
