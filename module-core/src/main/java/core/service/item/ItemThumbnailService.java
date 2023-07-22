package core.service.item;

import core.repository.item.ItemThumbnailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ItemThumbnailService {

    private final ItemThumbnailRepository itemThumbnailRepository;
}
