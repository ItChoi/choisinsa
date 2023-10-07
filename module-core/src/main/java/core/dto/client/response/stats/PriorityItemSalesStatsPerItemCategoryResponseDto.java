package core.dto.client.response.stats;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PriorityItemSalesStatsPerItemCategoryResponseDto {
    private Long rootItemCategoryId;
    private Long parentItemCategoryId;
    private Long itemCategoryId;
    private Long itemId;
    private int totalQuantity;
}
