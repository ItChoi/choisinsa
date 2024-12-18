package core.repository.hashtag;

import core.common.enumeration.hashtag.ItemHashTagType;
import core.domain.hashtag.ItemHashTagMapping;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemHashTagMappingRepository extends JpaRepository<ItemHashTagMapping, Long> {

    List<ItemHashTagMapping> findAllByItemIdAndType(Long itemId,
                                                    ItemHashTagType type);
}