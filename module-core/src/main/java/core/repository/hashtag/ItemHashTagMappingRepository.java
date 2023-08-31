package core.repository.hashtag;

import com.mall.choisinsa.enumeration.hashtag.ItemHashTagType;
import core.domain.hashtag.ItemHashTagMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ItemHashTagMappingRepository extends JpaRepository<ItemHashTagMapping, Long> {

    List<ItemHashTagMapping> findAllByItemIdAndType(Long itemId,
                                                    ItemHashTagType type);
}