package core.service.stats;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ItemStatsService {
    private final ItemSalesStatsService itemSalesStatsService;


    public Object findMostItemSalesPerTopItemCategories(Integer itemSize) {
        if (itemSize == null || itemSize <= 0) {
            itemSize = 20;
        }


        return null;
    }
}
