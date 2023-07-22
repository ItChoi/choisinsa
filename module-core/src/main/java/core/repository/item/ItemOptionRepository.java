package core.repository.item;

import core.domain.item.ItemOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ItemOptionRepository extends JpaRepository<ItemOption, Long> {

}