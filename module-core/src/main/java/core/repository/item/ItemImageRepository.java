package core.repository.item;

import core.common.enumeration.item.ItemImageType;
import core.domain.item.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {

    Optional<ItemImage> findByItemIdAndItemImageType(Long itemId, ItemImageType itemImageType);
}