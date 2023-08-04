package core.service.item;

import com.mall.choisinsa.enumeration.item.ItemImageType;
import core.aws.s3.AwsS3Support;
import core.domain.item.ItemImage;
import core.domain.item.ItemThumbnail;
import core.dto.admin.request.item.ItemInsertStep1RequestDto.ItemThumbnailImageRequestDto;
import core.repository.item.ItemImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

        itemThumbnailService.saveAll(itemImage.getItemId(), requestDtos);

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
