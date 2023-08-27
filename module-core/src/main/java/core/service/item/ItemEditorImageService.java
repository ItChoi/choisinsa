package core.service.item;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import core.aws.s3.AwsS3Support;
import core.aws.s3.S3FolderType;
import core.domain.item.ItemEditorImage;
import core.dto.admin.request.item.AdminItemEditorImageRequestDto;
import core.repository.item.ItemEditorImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class ItemEditorImageService {

    private final ItemEditorImageRepository itemEditorImageRepository;

    @Transactional
    public void upsertItemEditorImage(Long itemEditorContentId,
                                      AdminItemEditorImageRequestDto image) {
        MultipartFile file = image.getFile();
        // 파일 등록이 목적이 아닌, displayOrder 수정을 목적으로 할 시 파일이 존재하지 않음
        if (file == null || file.isEmpty()) {
            return;
        }

        String filename = file.getOriginalFilename();
        String fileUrl = AwsS3Support.uploadTest(S3FolderType.ITEM_EDITOR_CONTENT, itemEditorContentId, file);

        Long itemEditorImageId = image.getItemEditorImageId();
        if (itemEditorImageId == null) {
            itemEditorImageRepository.save(ItemEditorImage.builder()
                    .itemEditorContentId(itemEditorContentId)
                    .filename(filename)
                    .fileUrl(fileUrl)
                    .build());
        } else {
            ItemEditorImage itemEditorImage = findByIdAndItemEditorContentIdOrThrow(itemEditorImageId, itemEditorContentId);
            String removeUrlInRemoteRepo = itemEditorImage.getFileUrl();
            if (StringUtils.hasText(removeUrlInRemoteRepo)) {
                AwsS3Support.delete(removeUrlInRemoteRepo);
            }

            itemEditorImage.setFilename(filename);
            itemEditorImage.setFileUrl(fileUrl);
        }
    }

    private ItemEditorImage findByIdAndItemEditorContentIdOrThrow(Long itemEditorImageId,
                                                                  Long itemEditorContentId) {

        ItemEditorImage itemEditorImage = itemEditorImageRepository.findById(itemEditorImageId)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.NOT_FOUND_ITEM_EDITOR_MARKUP_TEXT));

        if (!itemEditorImage.getItemEditorContentId().equals(itemEditorContentId)) {
            throw new ErrorTypeAdviceException(ErrorType.MISMATCH_REQUEST);
        }

        return itemEditorImage;
    }
}
