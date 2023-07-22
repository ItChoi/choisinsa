package core.service.item;

import core.repository.item.ItemPurchaseSatisfactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ItemPurchaseSatisfactionService {

    private final ItemPurchaseSatisfactionRepository itemPurchaseSatisfactionRepository;
}
