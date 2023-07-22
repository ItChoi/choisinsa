package core.repository.item;

import core.domain.item.ItemEditorImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ItemEditorImageRepository extends JpaRepository<ItemEditorImage, Long> {

}