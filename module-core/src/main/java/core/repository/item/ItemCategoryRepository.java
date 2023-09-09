package core.repository.item;

import core.domain.item.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {

    List<ItemCategory> findALlByParentIdIsNullOrderByDisplayOrderAsc();

}