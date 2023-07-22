package core.service.item;

import core.repository.item.ItemEditorImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ItemEditorImageService {

    private final ItemEditorImageRepository itemEditorImageRepository;

}
