package core.service.stats;

import core.domain.item.ItemCategory;
import core.domain.stats.ItemSalesStats;
import core.dto.client.response.stats.ItemSalesStatsWrapperResponseDto;
import core.dto.client.response.stats.PriorityItemSalesStatsPerItemCategoryResponseDto;
import core.repository.stats.ItemSalesStatsRepository;
import core.service.item.ItemCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemSalesStatsService {

    private final ItemSalesStatsRepository itemSalesStatsRepository;
    private final ItemCategoryService itemCategoryService;

    @Transactional(readOnly = true)
    public List<PriorityItemSalesStatsPerItemCategoryResponseDto> findPriorityItemSalesStatsPerItemCategoryResponseDtoAllBy(Integer year) {
        return itemSalesStatsRepository.findItemSalesStatsPerItemCategoryResponseDtoAllBy(year);
    }

    @Transactional(readOnly = true)
    public ItemSalesStatsWrapperResponseDto findItemSalesStatsWrapperResponseDtoBy(Integer year) {
        return getItemSalesStatsWrapperResponseDto(year);
    }

    private ItemSalesStatsWrapperResponseDto getItemSalesStatsWrapperResponseDto(Integer year) {
        List<PriorityItemSalesStatsPerItemCategoryResponseDto> priorityItemSalesStatistics = findPriorityItemSalesStatsPerItemCategoryResponseDtoAllBy(year);

        Map<Long, List<PriorityItemSalesStatsPerItemCategoryResponseDto>> priorityItemSalesStatisticsWithItemCategoryId = new LinkedHashMap<>();
        for (PriorityItemSalesStatsPerItemCategoryResponseDto priorityItemSalesStats : priorityItemSalesStatistics) {
            Long key = priorityItemSalesStats.getRootItemCategoryId() == null ? priorityItemSalesStats.getItemCategoryId() : priorityItemSalesStats.getRootItemCategoryId();

            List<PriorityItemSalesStatsPerItemCategoryResponseDto> values = priorityItemSalesStatisticsWithItemCategoryId.get(key);
            if (CollectionUtils.isEmpty(values)) {
                values = new ArrayList<>();
                priorityItemSalesStatisticsWithItemCategoryId.put(key, values);
            }

            values.add(priorityItemSalesStats);
        }

        return new ItemSalesStatsWrapperResponseDto(priorityItemSalesStatistics, priorityItemSalesStatisticsWithItemCategoryId);
    }
}
