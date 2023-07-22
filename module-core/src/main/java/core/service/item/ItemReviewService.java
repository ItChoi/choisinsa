package core.service.item;

import core.repository.item.ItemReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ItemReviewService {

    private final ItemReviewRepository itemReviewRepository;
}
