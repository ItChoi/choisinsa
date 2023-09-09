package core.service.item;

import core.domain.item.ItemCategory;
import core.repository.item.ItemCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ItemCategoryService {

    private final ItemCategoryRepository itemCategoryRepository;

    @Transactional(readOnly = true)
    public boolean existsById(Long itemCategoryId) {
        return itemCategoryRepository.existsById(itemCategoryId);
    }

    @Transactional(readOnly = true)
    public List<ItemCategory> findALlByParentIdIsNullOrderByDisplayOrderAsc() {
        return itemCategoryRepository.findALlByParentIdIsNullOrderByDisplayOrderAsc();
    }
}

































