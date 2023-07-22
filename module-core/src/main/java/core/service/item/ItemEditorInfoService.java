package core.service.item;

import core.repository.item.ItemEditorInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ItemEditorInfoService {

    private final ItemEditorInfoRepository itemEditorInfoRepository;
}
