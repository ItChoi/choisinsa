package core.repository.item;

import core.domain.item.ItemCategory;
import core.domain.stats.ItemSalesStats;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long>, ItemCategoryRepositoryCustom {

    List<ItemCategory> findAllByParentIdIsNullOrderByDisplayOrderAsc();

    List<ItemCategory> findAllByParentIdIsNull();
}