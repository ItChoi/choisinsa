package core.repository.item;

import core.domain.item.ItemEditorContent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ItemEditorContentRepository extends JpaRepository<ItemEditorContent, Long> {

    List<ItemEditorContent> findByItemEditorInfoIdAndIdIn(Long itemEditorInfoId,
                                                       Collection<Long> itemEditorContentIds);

    void deleteByIdIn(Collection<Long> itemEditorContentIds);
}