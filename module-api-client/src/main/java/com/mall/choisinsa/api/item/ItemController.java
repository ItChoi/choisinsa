package com.mall.choisinsa.api.item;

import com.mall.choisinsa.dto.response.ResponseWrapper;
import core.dto.client.request.item.ItemDetailRequestDto;
import core.service.item.ItemService;
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

    @GetMapping("/{itemId}")
    public ResponseWrapper getItemDetail(@PathVariable Long itemId,
                                         ItemDetailRequestDto requestDto) {
        return ResponseWrapper.ok(itemService.findItemDetailInfoResponseDtoBy(itemId, requestDto));
    }


}
