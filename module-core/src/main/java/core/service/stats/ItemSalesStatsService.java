package core.service.stats;

import core.repository.stats.ItemSalesStatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ItemSalesStatsService {

    private final ItemSalesStatsRepository itemSalesStatsRepository;

}
