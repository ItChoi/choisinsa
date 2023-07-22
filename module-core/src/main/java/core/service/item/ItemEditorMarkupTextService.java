package core.service.item;

import core.repository.item.ItemEditorMarkupTextRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ItemEditorMarkupTextService {

    private final ItemEditorMarkupTextRepository itemEditorMarkupTextRepository;

}
