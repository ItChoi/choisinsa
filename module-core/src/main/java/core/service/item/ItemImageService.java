package core.service.item;

import core.repository.item.ItemImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ItemImageService {

    private final ItemImageRepository itemImageRepository;

}
