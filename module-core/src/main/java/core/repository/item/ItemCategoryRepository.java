package core.repository.item;

import core.domain.item.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {

}