package core.repository.item;

import core.domain.item.ItemDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ItemDetailRepository extends JpaRepository<ItemDetail, Long> {

}