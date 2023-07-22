package core.repository.item;

import core.domain.item.ItemEditorContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ItemEditorContentRepository extends JpaRepository<ItemEditorContent, Long> {

}