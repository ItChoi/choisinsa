package core.repository.item;

import core.domain.item.ItemPurchaseSatisfactionCategoryMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ItemPurchaseSatisfactionCategoryMappingRepository extends JpaRepository<ItemPurchaseSatisfactionCategoryMapping, Long> {

}