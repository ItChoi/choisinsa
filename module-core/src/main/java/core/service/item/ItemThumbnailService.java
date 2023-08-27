package core.service.item;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import core.aws.s3.AwsS3Support;
import core.aws.s3.S3FolderType;
import core.domain.item.Item;
import core.domain.item.ItemThumbnail;
import core.dto.admin.request.item.AdminItemThumbnailImageRequestDto;
import core.repository.item.ItemThumbnailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ItemThumbnailService {

    private final ItemThumbnailRepository itemThumbnailRepository;

    @Transactional
    public void saveAll(Item item,
                        Long itemImageId,
                        Collection<AdminItemThumbnailImageRequestDto> requestDtos) {
        if (CollectionUtils.isEmpty(requestDtos)) {
            return;
        }

        List<ItemThumbnail> itemThumbnails = new ArrayList<>();
        for (AdminItemThumbnailImageRequestDto requestDto : requestDtos) {
            Long itemThumbnailId = requestDto.getItemThumbnailId();

            if (itemThumbnailId != null) {
                updateItemThumbnail(itemImageId, itemThumbnailId, requestDto);
                continue;
            }

            int displayOrder = requestDto.getDisplayOrder();
            MultipartFile thumbnailFile = requestDto.getFile();

            if (!isAvailableUpload(displayOrder, thumbnailFile)) {
                log.error("ERROR: 파일이 등록되지 않았습니다.");
                continue;
            }

            itemThumbnails.add(ItemThumbnail.builder()
                    .itemImageId(itemImageId)
                    .displayOrder(displayOrder)
                    .filename(thumbnailFile.getOriginalFilename())
                    .fileUrl(
                            AwsS3Support.uploadTest(
                                    S3FolderType.ITEM,
                                    item.getId(),
                                    S3FolderType.ITEM_IMAGE,
                                    itemImageId,
                                    thumbnailFile
                            )
                    )
                    .build()
            );
        }

        if (!CollectionUtils.isEmpty(itemThumbnails)) {
            itemThumbnailRepository.saveAll(itemThumbnails);
        }
    }

    private void updateItemThumbnail(Long itemImageId,
                                     Long itemThumbnailId,
                                     AdminItemThumbnailImageRequestDto requestDto) {
        ItemThumbnail itemThumbnail = findByIdAndItemImageIdOrThrow(itemThumbnailId, itemImageId);

        itemThumbnail.setDisplayOrder(requestDto.getDisplayOrder());
    }

    private ItemThumbnail findByIdAndItemImageIdOrThrow(Long itemThumbnailId,
                                                        Long itemImageId) {
        if (itemThumbnailId == null || itemImageId == null) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_EXISTS_REQUIRED_DATA);
        }

        ItemThumbnail itemThumbnail = findByIdOrThrow(itemThumbnailId);
        if (!itemThumbnail.getItemImageId().equals(itemImageId)) {
            throw new ErrorTypeAdviceException(ErrorType.MISMATCH_REQUEST);
        }

        return itemThumbnail;
    }

    private ItemThumbnail findByIdOrThrow(Long itemThumbnailId) {
        return itemThumbnailRepository.findById(itemThumbnailId)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.NOT_FOUND_ITEM_THUMBNAIL));
    }

    private boolean isAvailableUpload(int displayOrder,
                                      MultipartFile file) {
        return displayOrder > 0 && file != null && !file.isEmpty();
    }
}
