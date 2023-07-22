package core.repository.item;

import core.domain.item.ItemOptionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ItemOptionDetailRepository extends JpaRepository<ItemOptionDetail, Long> {

}