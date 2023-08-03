package core.service.item;

import core.repository.item.ItemCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ItemCategoryService {

    private final ItemCategoryRepository itemCategoryRepository;

    @Transactional(readOnly = true)
    public boolean existsById(Long itemCategoryId) {
        return itemCategoryRepository.existsById(itemCategoryId);
    }
}
