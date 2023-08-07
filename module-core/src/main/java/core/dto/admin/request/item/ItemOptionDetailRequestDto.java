package core.dto.admin.request.item;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
public class ItemOptionDetailRequestDto {
    private String name;
    private Long addPrice;
    private int stockQuantity;

    public boolean isRegistrableData() {
        return StringUtils.hasText(name)
                && addPrice != null
                && addPrice > 0
                && stockQuantity > 0;
    }
}
