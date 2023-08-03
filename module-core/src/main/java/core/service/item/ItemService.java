package core.service.item;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import com.mall.choisinsa.enumeration.item.ItemStep;
import core.aws.s3.AwsS3Support;
import core.dto.admin.request.item.ItemInsertRequestDto;
import core.dto.admin.request.item.ItemInsertStep1RequestDto;
import core.repository.brand.BrandRepository;
import core.repository.item.ItemRepository;
import core.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemDetailService itemDetailService;
    private final ItemImageService imageService;
    private final ItemCategoryService itemCategoryService;
    private final BrandRepository brandRepository;

    @Transactional
    public void saveItemBy(Long memberId,
                           ItemInsertRequestDto requestDto) {
        if (!requestDto.isAvailableDataByStep()) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_EXISTS_REQUIRED_DATA);
        }

        Long companyId = requestDto.getCompanyId();
        Long brandId = requestDto.getBrandId();
        isAvailableAdmin(memberId, companyId, brandId);

        ItemStep step = requestDto.getStep();
        if (step == ItemStep.STEP_ONE) {
            saveItem(requestDto.getStep1Info());
        }

        if (step == ItemStep.STEP_TWO) {

        }

        if (step == ItemStep.STEP_THREE) {

        }
    }

    private void isAvailableAdmin(Long memberId,
                                  Long companyId,
                                  Long brandId) {
        //
        brandRepository.isRightBrandMember(memberId, companyId, brandId);

    }

    private void saveItem(ItemInsertStep1RequestDto step1Info) {
        if (!step1Info.isAvailableData()) {
            return;
        }

        validateItem(step1Info);
        MultipartFile itemMainImageFile = step1Info.getFile();
        String fileUrl = AwsS3Support.uploadTest(itemMainImageFile);
        String filename = itemMainImageFile.getOriginalFilename();


    }

    private void validateItem(ItemInsertStep1RequestDto step1Info) {
        if (!itemCategoryService.existsById(step1Info.getItemCategoryId())) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_EXISTS_ITEM_CATEGORY);
        }
    }
}
