package core.service.item;

import core.repository.item.ItemPurchaseSatisfactionAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ItemPurchaseSatisfactionAnswerService {

    private final ItemPurchaseSatisfactionAnswerRepository itemPurchaseSatisfactionAnswerRepository;
}
