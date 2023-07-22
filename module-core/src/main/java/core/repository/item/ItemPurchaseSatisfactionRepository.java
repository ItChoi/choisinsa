package core.repository.item;

import core.domain.item.ItemPurchaseSatisfaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ItemPurchaseSatisfactionRepository extends JpaRepository<ItemPurchaseSatisfaction, Long> {

}