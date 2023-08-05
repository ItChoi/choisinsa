package core.service.item;

import com.mall.choisinsa.enumeration.item.ItemImageType;
import core.domain.item.ItemImage;
import core.dto.admin.request.item.ItemThumbnailImageRequestDto;
import core.repository.item.ItemImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
@Service
public class ItemImageService {

    private final ItemImageRepository itemImageRepository;
    private final ItemThumbnailService itemThumbnailService;


    public void saveThumbnailImages(Long itemId,
                                    Collection<ItemThumbnailImageRequestDto> requestDtos) {
        if (itemId == null || CollectionUtils.isEmpty(requestDtos)) {
            return;
        }

        ItemImage itemImage = findOrSaveItemByItemId(itemId);
        itemThumbnailService.saveAll(itemId, itemImage.getId(), requestDtos);

    }

    private ItemImage findOrSaveItemByItemId(Long itemId) {
        return itemImageRepository.findByItemIdAndItemImageType(itemId, ItemImageType.THUMBNAIL)
                .orElseGet(() -> itemImageRepository.save(
                        ItemImage.builder()
                                .itemId(itemId)
                                .itemImageType(ItemImageType.THUMBNAIL)
                                .build()
                ));
    }
}
