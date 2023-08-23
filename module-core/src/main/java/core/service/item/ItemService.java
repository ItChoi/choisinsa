package core.service.item;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import com.mall.choisinsa.enumeration.item.ItemStatus;
import com.mall.choisinsa.enumeration.item.ItemStep;
import core.aws.s3.AwsS3Support;
import core.aws.s3.S3FolderType;
import core.domain.item.Item;
import core.domain.member.Member;
import core.dto.admin.request.item.ItemRequestDto;
import core.dto.admin.request.item.ItemUpsertRequestDto;
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
    public void upsertItemBy(Long memberId,
                             ItemUpsertRequestDto requestDto) {
        validateItem(memberId, requestDto);
        upsertItemByStep(requestDto);
    }

    private void upsertItemByStep(ItemUpsertRequestDto requestDto) {
        ItemStep step = requestDto.getStep();
        if (step == ItemStep.STEP_ONE) {
            upsertItemStep1(requestDto.getBrandId(), requestDto.getStep1Info());
        }

        if (step == ItemStep.STEP_TWO) {
            upsertItemStep2(requestDto.getStep2Info());
        }

        if (step == ItemStep.STEP_THREE) {
            upsertItemStep3(requestDto.getStep3Info());
        }
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

    private void upsertItemStep1(Long brandId,
                                 ItemRequestDto step1Info) {
        validateItemStep1(step1Info);
        Item item = upsertItemWithItemId(brandId, step1Info);

        itemOptionService.validateAfterRegister(item);
    }

    private Item upsertItemWithItemId(Long brandId,
                                      ItemRequestDto step1Info) {
        Long itemId = step1Info.getItemId();
        if (itemId == null) {
            // 등록
            return insertItem(brandId, step1Info);
        } else {
            // 수정
            return updateItem(itemId, step1Info);
        }
    }

    private Item updateItem(Long itemId,
                            ItemRequestDto step1Info) {
        Item item = findByIdOrThrow(itemId);
        ItemMapper.INSTANCE.updateItem(item, step1Info);
        upsertWithAdditionalItem(step1Info, item);

        return item;
    }

    private void upsertWithAdditionalItem(ItemRequestDto step1Info, Item item) {
        putItemMainImage(item, step1Info.getFile());
        // TODO: 수정 필요
        itemOptionService.upsertItemOptions(item, step1Info.getItemOptions());
        // TODO: 수정 필요
        itemImageService.upsertThumbnailImages(item, step1Info.getItemThumbnails());
    }

    private Item insertItem(Long brandId,
                            ItemRequestDto step1Info) {
        Item item = itemRepository.save(
                Item.builder()
                        .itemCategoryId(step1Info.getItemCategoryId())
                        .status(ItemStatus.INPUT)
                        .brandId(brandId)
                        .nameEn(step1Info.getItemNameEn())
                        .nameKo(step1Info.getItemNameKo())
                        .price(step1Info.getPrice())
                        .useTarget(step1Info.getUseTarget())
                        .totalStockQuantity(step1Info.getTotalStockQuantity())
                        .build()
        );

        upsertWithAdditionalItem(step1Info, item);

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

    private void upsertItemStep2(ItemDetailRequestDto step2Info) {
        itemDetailService.upsertItemDetail(step2Info);
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

    private void validateItemStep1(ItemRequestDto step1Info) {
        if (!step1Info.isAvailableData()) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }

        if (!itemCategoryService.existsById(step1Info.getItemCategoryId())) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_EXISTS_ITEM_CATEGORY);
        }
    }

    private void validateItem(Long memberId, ItemUpsertRequestDto requestDto) {
        if (!requestDto.isAvailableDataByStep()) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_EXISTS_REQUIRED_DATA);
        }

        if (!isAvailableItemRegistrationMember(memberId, requestDto.getCompanyId(), requestDto.getBrandId())) {
            throw new ErrorTypeAdviceException(ErrorType.MISMATCH_AUTHORITY);
        }
    }
}
