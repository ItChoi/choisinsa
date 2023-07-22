package core.repository.item;

import core.domain.item.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {

}