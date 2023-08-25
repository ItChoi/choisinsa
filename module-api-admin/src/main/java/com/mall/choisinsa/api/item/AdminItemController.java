package com.mall.choisinsa.api.item;

import com.mall.choisinsa.dto.response.ResponseWrapper;
import com.mall.choisinsa.security.dto.SecurityMemberDto;
import core.dto.admin.request.item.ItemDetailRequestDto;
import core.dto.admin.request.item.ItemEditorInfoRequestDto;
import core.dto.admin.request.item.ItemRequestDto;
import core.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/items")
public class AdminItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseWrapper insertItemIncludedFile(ItemRequestDto requestDto,
                                                  @AuthenticationPrincipal SecurityMemberDto loginUser) {
        itemService.insertItemWithFile(loginUser.getMemberId(), requestDto);
        return ResponseWrapper.ok();
    }

    @PostMapping("/{itemId}")
    public ResponseWrapper upsertItemIncludedFile(@PathVariable Long itemId,
                                                  ItemRequestDto requestDto,
                                                  @AuthenticationPrincipal SecurityMemberDto loginUser) {
        itemService.updateItemWithFile(loginUser.getMemberId(), itemId, requestDto);
        return ResponseWrapper.ok();
    }

    @PutMapping("/{itemId}/detail")
    public ResponseWrapper putItemDetail(@PathVariable Long itemId,
                                         @RequestBody ItemDetailRequestDto requestDto) {
        itemService.putItemDetail(itemId, requestDto);
        return ResponseWrapper.ok();
    }

    @PostMapping("/{itemId}/editor-infos")
    public ResponseWrapper insertItemEditorInfo(@PathVariable Long itemId,
                                                ItemEditorInfoRequestDto requestDto) {
        itemService.insertItemEditorInfo(itemId, requestDto);
        return ResponseWrapper.ok();
    }

    @PostMapping("/{itemId}/editor-infos/{itemEditorInfoId}")
    public ResponseWrapper updateItemEditorInfo(@PathVariable Long itemId,
                                                @PathVariable Long itemEditorInfoId,
                                                ItemEditorInfoRequestDto requestDto) {
        itemService.updateItemEditorInfo(itemId, itemEditorInfoId, requestDto);
        return ResponseWrapper.ok();
    }

}
