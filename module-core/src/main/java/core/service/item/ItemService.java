package core.service.item;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import com.mall.choisinsa.enumeration.item.ItemStatus;
import core.aws.s3.AwsS3Support;
import core.aws.s3.S3FolderType;
import core.domain.item.Item;
import core.domain.member.Member;
import core.dto.admin.request.item.ItemRequestDto;
import core.dto.admin.request.item.ItemDetailRequestDto;
import core.dto.admin.request.item.ItemEditorInfoRequestDto;
import core.mapper.item.ItemMapper;
import core.repository.brand.BrandRepository;
import core.repository.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemDetailService itemDetailService;
    private final ItemImageService itemImageService;
    private final ItemCategoryService itemCategoryService;
    private final ItemOptionService itemOptionService;
    private final ItemEditorInfoService itemEditorInfoService;

    private final ItemRepository itemRepository;
    private final BrandRepository brandRepository;

    @Transactional
    public void upsertItemIncludedFile(Long memberId,
                                       ItemRequestDto requestDto) {
        validateItem(memberId, requestDto);
        Item item = upsertItem(requestDto.getBrandId(), requestDto);
        itemOptionService.validateAfterRegister(item);
    }

    private boolean isAvailableItemRegistrationMember(Long memberId,
                                                      Long companyId,
                                                      Long brandId) {
        Member brandAdmin = findBrandAdminBy(memberId, companyId, brandId)
                .orElse(null);

        return brandAdmin != null;
    }

    private Optional<Member> findBrandAdminBy(Long memberId,
                                              Long companyId,
                                              Long brandId) {
        return brandRepository.findBrandAdminBy(memberId, companyId, brandId);
    }


    private Item upsertItem(Long brandId,
                            ItemRequestDto requestDto) {
        Long itemId = requestDto.getItemId();
        if (itemId == null) {
            // 등록
            return insertItem(brandId, requestDto);
        } else {
            // 수정
            return updateItem(itemId, requestDto);
        }
    }

    private Item updateItem(Long itemId,
                            ItemRequestDto requestDto) {
        Item item = findByIdOrThrow(itemId);
        ItemMapper.INSTANCE.updateItem(item, requestDto);
        putItemMainImage(item, requestDto.getFile());
        upsertWithAdditionalItem(requestDto, item);

        return item;
    }

    private void upsertWithAdditionalItem(ItemRequestDto step1Info, Item item) {
        itemOptionService.upsertItemOptions(item, step1Info.getItemOptions());
        itemImageService.upsertThumbnailImages(item, step1Info.getItemThumbnails());
    }

    private Item insertItem(Long brandId,
                            ItemRequestDto requestDto) {
        Item item = itemRepository.save(
                Item.builder()
                        .itemCategoryId(requestDto.getItemCategoryId())
                        .status(ItemStatus.INPUT)
                        .brandId(brandId)
                        .nameEn(requestDto.getItemNameEn())
                        .nameKo(requestDto.getItemNameKo())
                        .price(requestDto.getPrice())
                        .useTarget(requestDto.getUseTarget())
                        .totalStockQuantity(requestDto.getTotalStockQuantity())
                        .build()
        );

        putItemMainImage(item, requestDto.getFile());
        upsertWithAdditionalItem(requestDto, item);

        return item;
    }

    private static void putItemMainImage(Item item,
                                         MultipartFile itemMainImageFile) {
        if (item != null && itemMainImageFile != null && !itemMainImageFile.isEmpty()) {
            String removeUrlInRemoteRepo = item.getFileUrl();
            if (StringUtils.hasText(removeUrlInRemoteRepo)) {
                AwsS3Support.delete(removeUrlInRemoteRepo);
            }

            item.setFilename(itemMainImageFile.getOriginalFilename());
            item.setFileUrl(
                    AwsS3Support.uploadTest(S3FolderType.ITEM, item.getId(), itemMainImageFile)
            );
        }
    }


    @Transactional(readOnly = true)
    public Item findByIdOrThrow(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.NOT_FOUND_ITEM));
    }

    private void upsertItemStep3(ItemEditorInfoRequestDto step3Info) {
        validateItemStep3(step3Info);
        itemEditorInfoService.upsertItemEditorInfo(step3Info);
    }

    private void validateItemStep3(ItemEditorInfoRequestDto step3Info) {
        if (step3Info.isAvailableData()) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }
    }

    private void validateItem(Long memberId,
                              ItemRequestDto requestDto) {
        if (!isAvailableItemRegistrationMember(memberId, requestDto.getCompanyId(), requestDto.getBrandId())) {
            throw new ErrorTypeAdviceException(ErrorType.MISMATCH_AUTHORITY);
        }

        if (!requestDto.isAvailableData()) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }

        if (!itemCategoryService.existsById(requestDto.getItemCategoryId())) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_EXISTS_ITEM_CATEGORY);
        }
    }

    @Transactional
    public void putItemDetail(Long itemId,
                              ItemDetailRequestDto requestDto) {
        itemDetailService.putItemDetail(itemId, requestDto);
    }

    @Transactional
    public void insertItemEditorInfo(Long itemId,
                                     ItemEditorInfoRequestDto requestDto) {
        upsertItemStep3(requestDto);
    }
}
