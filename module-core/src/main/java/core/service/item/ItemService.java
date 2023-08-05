package core.service.item;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import com.mall.choisinsa.enumeration.item.ItemStatus;
import com.mall.choisinsa.enumeration.item.ItemStep;
import core.aws.s3.AwsS3Support;
import core.aws.s3.S3FolderType;
import core.domain.item.Item;
import core.domain.member.Member;
import core.dto.admin.request.item.ItemInsertRequestDto;
import core.dto.admin.request.item.ItemInsertStep1RequestDto;
import core.dto.admin.request.item.ItemInsertStep2RequestDto;
import core.dto.admin.request.item.ItemInsertStep3RequestDto;
import core.repository.brand.BrandRepository;
import core.repository.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemDetailService itemDetailService;
    private final ItemImageService itemImageService;
    private final ItemCategoryService itemCategoryService;
    private final ItemOptionService itemOptionService;

    private final ItemRepository itemRepository;
    private final BrandRepository brandRepository;

    @Transactional
    public void saveItemBy(Long memberId,
                           ItemInsertRequestDto requestDto) {
        validateItem(memberId, requestDto);
        saveItemByStep(requestDto);
    }

    private void saveItemByStep(ItemInsertRequestDto requestDto) {
        ItemStep step = requestDto.getStep();
        if (step == ItemStep.STEP_ONE) {
            saveItem(requestDto.getBrandId(), requestDto.getStep1Info());
        }

        if (step == ItemStep.STEP_TWO) {
            saveItem(requestDto.getStep2Info());
        }

        if (step == ItemStep.STEP_THREE) {
            saveItem(requestDto.getStep3Info());
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

    private void saveItem(Long brandId,
                          ItemInsertStep1RequestDto step1Info) {
        validateItemStep1(step1Info);

        Item savedItem = itemRepository.save(
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

        Long itemId = savedItem.getId();
        MultipartFile itemMainImageFile = step1Info.getFile();
        if (itemMainImageFile != null) {
            savedItem.setFilename(itemMainImageFile.getOriginalFilename());
            savedItem.setFileUrl(
                    AwsS3Support.uploadTest(S3FolderType.ITEM, itemId, itemMainImageFile)
            );
        }

        itemOptionService.saveItemOptions(savedItem, step1Info.getItemOptions());
        itemImageService.saveThumbnailImages(itemId, step1Info.getItemThumbnails());

        // TODO: 수량 체크 필요, Item - 수량은, 옵션 수량보다 높아야 한다. 같아야 하는 건 아니다.
        validateItemQuantity();
    }

    private void saveItem(ItemInsertStep2RequestDto step2Info) {
        itemDetailService.saveItemDetail(step2Info);
    }

    private void saveItem(ItemInsertStep3RequestDto step3Info) {

    }

    private void validateItemStep1(ItemInsertStep1RequestDto step1Info) {
        if (!step1Info.isAvailableData()) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }

        if (!itemCategoryService.existsById(step1Info.getItemCategoryId())) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_EXISTS_ITEM_CATEGORY);
        }
    }

    private void validateItem(Long memberId, ItemInsertRequestDto requestDto) {
        if (!requestDto.isAvailableDataByStep()) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_EXISTS_REQUIRED_DATA);
        }

        if (!isAvailableItemRegistrationMember(memberId, requestDto.getCompanyId(), requestDto.getBrandId())) {
            throw new ErrorTypeAdviceException(ErrorType.MISMATCH_AUTHORITY);
        }
    }
}
