package core.service.stats;

import core.dto.client.response.stats.ItemSalesStatsWrapperResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ItemStatsService {
    private final ItemSalesStatsService itemSalesStatsService;

    @Transactional(readOnly = true)
    public ItemSalesStatsWrapperResponseDto findItemSalesStatsWrapperResponseDtoBy(Integer year) {
        return itemSalesStatsService.findItemSalesStatsWrapperResponseDtoBy(year);
    }
}