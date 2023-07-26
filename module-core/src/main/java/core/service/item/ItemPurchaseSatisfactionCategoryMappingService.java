package core.service.item;

import core.repository.item.ItemPurchaseSatisfactionCategoryMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ItemPurchaseSatisfactionCategoryMappingService {

    private final ItemPurchaseSatisfactionCategoryMappingRepository itemPurchaseSatisfactionCategoryMappingRepository;

}
