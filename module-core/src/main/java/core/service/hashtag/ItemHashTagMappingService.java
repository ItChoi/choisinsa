package core.service.hashtag;

import core.common.exception.ErrorTypeAdviceException;
import core.common.exception.ErrorType;
import core.common.enumeration.hashtag.ItemHashTagType;
import core.domain.hashtag.ItemHashTagMapping;
import core.dto.client.response.item.ItemHashTagResponseDto;
import core.repository.hashtag.ItemHashTagMappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemHashTagMappingService {

    private final ItemHashTagMappingRepository itemHashTagMappingRepository;
    private final HashTagService hashTagService;

    @Transactional(readOnly = true)
    public List<ItemHashTagResponseDto> findItemHashTagResponseDtoAllBy(Long itemId,
                                                                        ItemHashTagType type) {
        if (itemId == null || type == null) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_EXISTS_REQUIRED_DATA);
        }

        List<Long> hashTagIds = itemHashTagMappingRepository.findAllByItemIdAndType(itemId, type).stream()
                .sorted(Comparator.comparingInt(ItemHashTagMapping::getDisplayOrder))
                .map(ItemHashTagMapping::getHashTagId)
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(hashTagIds)) {
            return Collections.emptyList();
        }

        return hashTagService.findByIdIn(hashTagIds).stream()
                .map(ItemHashTagResponseDto::new)
                .collect(Collectors.toList());
    }
}
