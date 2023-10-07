package core.repository.stats;

import core.dto.client.response.stats.PriorityItemSalesStatsPerItemCategoryResponseDto;

import java.util.List;

public interface ItemSalesStatsRepositoryCustom {
    List<PriorityItemSalesStatsPerItemCategoryResponseDto> findItemSalesStatsPerItemCategoryResponseDtoAllBy(Integer year);

}
