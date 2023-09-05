package core.service.item;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import core.domain.item.ItemEditorInfo;
import core.dto.admin.request.item.AdminItemEditorInfoRequestDto;
import core.dto.client.response.item.ItemEditorInfoResponseDto;
import core.mapper.item.ItemEditorInfoMapper;
import core.repository.item.ItemEditorInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ItemEditorInfoService {

    private final ItemEditorInfoRepository itemEditorInfoRepository;
    private final ItemEditorContentService itemEditorContentService;

    private void clearMainItemEditorInfo(Long itemId) {
        itemEditorInfoRepository.findByItemIdAndIsMain(itemId, true)
                .ifPresent(itemEditorInfo -> itemEditorInfo.setIsMain(false));
    }

    @Transactional
    public void updateItemEditorInfo(Long itemId,
                                      Long itemEditorInfoId,
                                      AdminItemEditorInfoRequestDto requestDto) {
        if (BooleanUtils.isTrue(requestDto.getIsMain())) {
            clearMainItemEditorInfo(itemId);
        }

        ItemEditorInfo itemEditorInfo = findByIdAndItemEditorInfoIdOrThrow(itemEditorInfoId, itemId);
        itemEditorInfo.setIsMain(requestDto.getIsMain());
        itemEditorInfo.setTitle(requestDto.getTitle());

        itemEditorContentService.upsertItemEditorContents(itemEditorInfo.getId(), requestDto.getItemEditorContents());
    }

    @Transactional
    public void insertItemEditorInfo(Long itemId,
                                     AdminItemEditorInfoRequestDto requestDto) {
        if (BooleanUtils.isTrue(requestDto.getIsMain())) {
            clearMainItemEditorInfo(itemId);
        }

        ItemEditorInfo itemEditorInfo = itemEditorInfoRepository.save(ItemEditorInfo.builder()
                .itemId(itemId)
                .title(requestDto.getTitle())
                .isMain(requestDto.getIsMain())
                .build());

        itemEditorContentService.upsertItemEditorContents(itemEditorInfo.getId(), requestDto.getItemEditorContents());
    }

    private ItemEditorInfo findByIdAndItemEditorInfoIdOrThrow(Long itemEditorInfoId,
                                                              Long itemId) {

        ItemEditorInfo itemEditorInfo = itemEditorInfoRepository.findById(itemEditorInfoId)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.NOT_FOUND_ITEM_EDITOR_INFO));

        if (!itemEditorInfo.getItemId().equals(itemId)) {
            throw new ErrorTypeAdviceException(ErrorType.MISMATCH_REQUEST);
        }
        return itemEditorInfo;
    }

    @Transactional
    public void deleteByItemIdAndIdIn(Long itemId,
                                      Collection<Long> itemEditorInfoIds) {
        if (itemId == null || CollectionUtils.isEmpty(itemEditorInfoIds)) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_EXISTS_REQUIRED_DATA);
        }

        validateItemEditorInfoForDelete(itemId, itemEditorInfoIds);
        itemEditorInfoRepository.deleteByIdIn(itemEditorInfoIds);
    }

    private void validateItemEditorInfoForDelete(Long itemId,
                                                 Collection<Long> itemEditorInfoIds) {
        List<ItemEditorInfo> itemEditorInfos = findByItemIdAndIdIn(itemId, itemEditorInfoIds);
        if (itemEditorInfos.size() != itemEditorInfoIds.size()) {
            throw new ErrorTypeAdviceException(ErrorType.MISMATCH_REQUEST);
        }

        for (ItemEditorInfo itemEditorInfo : itemEditorInfos) {
            if (itemEditorInfo.getIsMain()) {
                log.warn("메인 상품 에디터는 삭제 할 수 없습니다. editor title: []", itemEditorInfo.getTitle());
                throw new ErrorTypeAdviceException(ErrorType.CAN_NOT_DELETE_STATUS);
            }
        }
    }


    private List<ItemEditorInfo> findByItemIdAndIdIn(Long itemId,
                                                     Collection<Long> itemEditorInfoIds) {
        return itemEditorInfoRepository.findByItemIdAndIdIn(itemId, itemEditorInfoIds);
    }

    @Transactional
    public void deleteItemEditorContentByItemEditorInfoIdAndIdIn(Long itemId,
                                                                 Long itemEditorInfoId,
                                                                 Collection<Long> itemEditorContentIds) {
        validateItemEditorInfo(itemId, itemEditorInfoId);
        itemEditorContentService.deleteItemEditorContentByItemEditorInfoIdAndIdIn(itemEditorInfoId, itemEditorContentIds);
    }

    private void validateItemEditorInfo(Long itemId,
                                        Long itemEditorInfoId) {
        findByIdAndItemEditorInfoIdOrThrow(itemEditorInfoId, itemId);
    }

    @Transactional(readOnly = true)
    public ItemEditorInfoResponseDto findItemEditorInfoResponseDtoBy(Long itemId) {
        ItemEditorInfo itemEditorInfo = findMainItemEditorInfoByItemIdOrThrow(itemId);
        itemEditorContentService.orderByDisplayOrder(itemEditorInfo);
        ItemEditorInfoResponseDto responseDto = ItemEditorInfoMapper.INSTANCE.toItemEditorInfoResponseDto(itemEditorInfo);

        return responseDto;
    }

    private ItemEditorInfo findMainItemEditorInfoByItemIdOrThrow(Long itemId) {
        return itemEditorInfoRepository.findByItemIdAndIsMain(itemId, true)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.NOT_FOUND_ITEM_EDITOR_INFO));
    }

}
