package core.domain.stats;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ItemSalesStatsCompositeKey implements Serializable {
    private Long id;
    private LocalDateTime salesDate;
}
