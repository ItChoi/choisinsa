package core.service.item;

import core.repository.item.ItemEditorContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ItemEditorContentService {

    private final ItemEditorContentRepository itemEditorContentRepository;
}
