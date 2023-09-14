package com.mall.choisinsa.api.item;

import com.mall.choisinsa.dto.response.ResponseWrapper;
import core.dto.client.request.item.ItemDetailRequestDto;
import core.service.item.ItemService;
import core.service.stats.ItemStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;
    private final ItemStatsService itemStatsService;

    @GetMapping("/{itemId}")
    public ResponseWrapper getItemDetail(@PathVariable Long itemId,
                                         ItemDetailRequestDto requestDto) {
        return ResponseWrapper.ok(itemService.findItemDetailInfoResponseDtoBy(itemId, requestDto));
    }

    // 레디스로 조회 하도록
    // TODO: TEST
    /*@GetMapping("/count-per-category")
    public ResponseWrapper getItemCountByCategory() {
        return ResponseWrapper.ok(itemService.findItemCountAllPerCategoryApplicationReadyDtoAll());
    }*/

    // 레디스로 조회 하도록
    // TODO: TEST
    @GetMapping("/most-sales-per-top-item-categories")
    public ResponseWrapper getMostItemSalesPerTopItemCategories(Integer itemSize) {
        return ResponseWrapper.ok(itemStatsService.findMostItemSalesPerTopItemCategories(itemSize));
    }
}
