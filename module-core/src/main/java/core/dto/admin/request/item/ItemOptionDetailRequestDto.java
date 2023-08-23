package core.dto.admin.request.item;

import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
public class ItemOptionDetailRequestDto {
    private Long itemIoptionDetailId;
    @Setter
    private String name;
    @Setter
    private Long addPrice;
    @Setter
    private int stockQuantity;

    public boolean isRegistrableData() {
        return StringUtils.hasText(name)
                && addPrice != null
                && addPrice > 0
                && stockQuantity > 0;
    }
}
