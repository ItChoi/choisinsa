package core.service.item;

import core.aws.s3.AwsS3Support;
import core.aws.s3.S3FolderType;
import core.domain.item.Item;
import core.domain.item.ItemThumbnail;
import core.dto.admin.request.item.ItemThumbnailImageRequestDto;
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
                        Collection<ItemThumbnailImageRequestDto> requestDtos) {
        if (CollectionUtils.isEmpty(requestDtos)) {
            return;
        }

        List<ItemThumbnail> itemThumbnails = new ArrayList<>();
        for (ItemThumbnailImageRequestDto requestDto : requestDtos) {
            int displayOrder = requestDto.getDisplayOrder();
            MultipartFile thumbnailFile = requestDto.getFile();

            if (!isAvailableUpload(displayOrder, thumbnailFile)) {
                log.error("ERROR: 파일이 등록되지 않았습니다.");
                continue;
            }

            itemThumbnails.add(
                    ItemThumbnail.builder()
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

    private boolean isAvailableUpload(int displayOrder,
                                      MultipartFile file) {
        return displayOrder > 0 && file != null && !file.isEmpty();
    }
}
