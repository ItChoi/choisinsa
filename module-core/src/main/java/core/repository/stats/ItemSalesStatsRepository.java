package core.repository.stats;

import core.domain.stats.ItemSalesStats;
import core.domain.stats.ItemSalesStatsCompositeKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemSalesStatsRepository extends JpaRepository<ItemSalesStats, ItemSalesStatsCompositeKey> {

}