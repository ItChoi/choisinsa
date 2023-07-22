package core.service.item;

import core.domain.item.ItemPurchaseSatisfactionCategoryMapping;
import core.repository.item.ItemPurchaseSatisfactionCategoryMappingRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ItemPurchaseSatisfactionCategoryMappingService {

    @Autowired
    private ItemPurchaseSatisfactionCategoryMappingRepository itemPurchaseSatisfactionCategoryMappingRepository;

    public Long save(ItemPurchaseSatisfactionCategoryMappingVO vO) {
        ItemPurchaseSatisfactionCategoryMapping bean = new ItemPurchaseSatisfactionCategoryMapping();
        BeanUtils.copyProperties(vO, bean);
        bean = itemPurchaseSatisfactionCategoryMappingRepository.save(bean);
        return bean.getId();
    }

    public void delete(Long id) {
        itemPurchaseSatisfactionCategoryMappingRepository.deleteById(id);
    }

    public void update(Long id, ItemPurchaseSatisfactionCategoryMappingUpdateVO vO) {
        ItemPurchaseSatisfactionCategoryMapping bean = requireOne(id);
        BeanUtils.copyProperties(vO, bean);
        itemPurchaseSatisfactionCategoryMappingRepository.save(bean);
    }

    public ItemPurchaseSatisfactionCategoryMappingDTO getById(Long id) {
        ItemPurchaseSatisfactionCategoryMapping original = requireOne(id);
        return toDTO(original);
    }

    public Page<ItemPurchaseSatisfactionCategoryMappingDTO> query(ItemPurchaseSatisfactionCategoryMappingQueryVO vO) {
        throw new UnsupportedOperationException();
    }

    private ItemPurchaseSatisfactionCategoryMappingDTO toDTO(ItemPurchaseSatisfactionCategoryMapping original) {
        ItemPurchaseSatisfactionCategoryMappingDTO bean = new ItemPurchaseSatisfactionCategoryMappingDTO();
        BeanUtils.copyProperties(original, bean);
        return bean;
    }

    private ItemPurchaseSatisfactionCategoryMapping requireOne(Long id) {
        return itemPurchaseSatisfactionCategoryMappingRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Resource not found: " + id));
    }
}
