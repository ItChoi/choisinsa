package core.repository.item;

import com.mall.choisinsa.enumeration.item.ItemImageType;
import core.domain.item.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {

    Optional<ItemImage> findByItemIdAndItemImageType(Long itemId, ItemImageType itemImageType);
}