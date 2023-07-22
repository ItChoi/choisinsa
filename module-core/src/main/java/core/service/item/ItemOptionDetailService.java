package core.service.item;

import core.repository.item.ItemOptionDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ItemOptionDetailService {

    private final ItemOptionDetailRepository itemOptionDetailRepository;
}
