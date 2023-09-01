package com.mall.choisinsa.api.item;

import com.mall.choisinsa.dto.response.ResponseWrapper;
import com.mall.choisinsa.security.dto.SecurityMemberDto;
import core.dto.client.request.item.ItemDetailRequestDto;
import core.dto.client.request.item.ItemRequestDto;
import core.service.item.AdminItemService;
import core.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/{itemId}")
    public ResponseWrapper getItemDetail(@PathVariable Long itemId,
                                         ItemDetailRequestDto requestDto) {
        return ResponseWrapper.ok(itemService.getItemDetail(itemId, requestDto));
    }


}
