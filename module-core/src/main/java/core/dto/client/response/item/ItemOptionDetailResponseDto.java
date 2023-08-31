package core.dto.client.response.item;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ItemOptionDetailResponseDto {
    private Long id;
    private Long itemOptionId;
    private String name;
    private Long addPrice;
    private int stockQuantity;
    private int displayOrder;
}
