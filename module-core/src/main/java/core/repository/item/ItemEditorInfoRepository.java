package core.repository.item;

import core.domain.item.ItemEditorInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ItemEditorInfoRepository extends JpaRepository<ItemEditorInfo, Long> {

    Optional<ItemEditorInfo> findByItemIdAndIsMain(Long itemId,
                                                   Boolean isMain);

}