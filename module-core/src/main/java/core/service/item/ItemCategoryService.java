package core.service.item;

import core.repository.item.ItemCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ItemCategoryService {

    private final ItemCategoryRepository itemCategoryRepository;
}
