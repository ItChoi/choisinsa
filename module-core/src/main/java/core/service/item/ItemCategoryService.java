package core.service.item;

import core.domain.item.ItemCategory;
import core.domain.stats.ItemSalesStats;
import core.dto.client.response.item.ItemCountAllPerCategoryApplicationReadyDto;
import core.dto.client.response.item.ItemCountPerItemCategoryResponseDto;
import core.repository.item.ItemCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemCategoryService {

    private final ItemCategoryRepository itemCategoryRepository;

    @Transactional(readOnly = true)
    public boolean existsById(Long itemCategoryId) {
        return itemCategoryRepository.existsById(itemCategoryId);
    }

    @Transactional(readOnly = true)
    public List<ItemCategory> findALlByParentIdIsNullOrderByDisplayOrderAsc() {
        return itemCategoryRepository.findAllByParentIdIsNullOrderByDisplayOrderAsc();
    }

    @Transactional(readOnly = true)
    public List<ItemCountAllPerCategoryApplicationReadyDto> findItemCountAllPerCategoryApplicationReadyDtoAll() {
        List<ItemCategory> topItemCategories = findALlByParentIdIsNullOrderByDisplayOrderAsc();
        return convertToSortedItemCategoryApplicationReadyDto(topItemCategories);
    }

    private List<Long> extractItemCategoryIds(Collection<ItemCountAllPerCategoryApplicationReadyDto> responseDtos) {
        List<Long> extractedIcIds = new ArrayList<>();
        extractItemCategoryIds(responseDtos, extractedIcIds);

        return extractedIcIds;
    }

    private static void extractItemCategoryIds(Collection<ItemCountAllPerCategoryApplicationReadyDto> responseDtos,
                                               Collection<Long> extractedIcIds) {
        if (CollectionUtils.isEmpty(responseDtos)) {
            return;
        }

        extractedIcIds.addAll(responseDtos.stream()
                .map(ItemCountAllPerCategoryApplicationReadyDto::getItemCategoryId)
                .collect(Collectors.toList()));

        for (ItemCountAllPerCategoryApplicationReadyDto responseDto : responseDtos) {
            Set<ItemCountAllPerCategoryApplicationReadyDto> children = responseDto.getChildren();

            if (CollectionUtils.isEmpty(children)) {
                continue;
            }

            extractItemCategoryIds(children, extractedIcIds);
        }
    }

    private void calculateItemCountAllPerCategory(Collection<ItemCountAllPerCategoryApplicationReadyDto> itemCategoryDtos) {
        List<Long> extractedItemCategoryIds = extractItemCategoryIds(itemCategoryDtos);
        if (CollectionUtils.isEmpty(extractedItemCategoryIds)) {
            return;
        }

        Map<Long, Long> itemCountWithItemCategoryId = itemCategoryRepository.findItemCountPerItemCategoryResponseDtoAllByItemCategoryIds(extractedItemCategoryIds).stream()
                .collect(Collectors.toMap(ItemCountPerItemCategoryResponseDto::getItemCategoryId, ItemCountPerItemCategoryResponseDto::getItemCount));

        for (ItemCountAllPerCategoryApplicationReadyDto itemCategoryDto : itemCategoryDtos) {
            calculateTotalItemCountRecursively(itemCategoryDto, itemCountWithItemCategoryId);
        }
    }

    private long calculateTotalItemCountRecursively(ItemCountAllPerCategoryApplicationReadyDto itemCategoryDto,
                                                    Map<Long, Long> itemCountWithItemCategoryId) {

        if (itemCategoryDto == null) {
            return 0;
        }

        Long itemCountOrDefaultZero = itemCountWithItemCategoryId.getOrDefault(itemCategoryDto.getItemCategoryId(), 0L);
        itemCategoryDto.setItemCount(itemCountOrDefaultZero);

        Set<ItemCountAllPerCategoryApplicationReadyDto> children = itemCategoryDto.getChildren();
        if (CollectionUtils.isEmpty(children)) {
            itemCategoryDto.setIncludedLowerCategoryTotalItemCount(itemCountOrDefaultZero);
            return itemCountOrDefaultZero;
        }

        long tempTotalCount = 0;
        for (ItemCountAllPerCategoryApplicationReadyDto child : children) {
            tempTotalCount += calculateTotalItemCountRecursively(child, itemCountWithItemCategoryId);
        }

        long includedLowerCategoryTotalItemCount = itemCountOrDefaultZero + tempTotalCount;
        itemCategoryDto.setIncludedLowerCategoryTotalItemCount(includedLowerCategoryTotalItemCount);

        return includedLowerCategoryTotalItemCount;
    }

    private List<ItemCountAllPerCategoryApplicationReadyDto> convertToSortedItemCategoryApplicationReadyDto(Collection<ItemCategory> itemCategories) {
        if (CollectionUtils.isEmpty(itemCategories)) {
            return Collections.emptyList();
        }

        List<ItemCountAllPerCategoryApplicationReadyDto> responseDtos = itemCategories.stream()
                .map(ItemCountAllPerCategoryApplicationReadyDto::new)
                .collect(Collectors.toList());

        calculateItemCountAllPerCategory(responseDtos);
        return responseDtos;

    }
}
