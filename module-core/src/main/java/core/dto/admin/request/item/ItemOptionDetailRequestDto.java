package core.dto.admin.request.item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemOptionDetailRequestDto {
    private String name;
    private Long addPrice;
    private int stockQuantity;
}
