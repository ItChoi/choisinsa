package core.dto.client.response.stats;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;


@AllArgsConstructor
@Getter
public class ItemSalesStatsWrapperResponseDto {
    private List<PriorityItemSalesStatsPerItemCategoryResponseDto> priorityItemSalesStatistics;
    private Map<Long, List<PriorityItemSalesStatsPerItemCategoryResponseDto>> priorityItemSalesStatisticsWithRootParentId;
}
