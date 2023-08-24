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
    public ResponseWrapper upsertItemIncludedFile(ItemRequestDto requestDto,
                                                  @AuthenticationPrincipal SecurityMemberDto loginUser) {
        itemService.upsertItemIncludedFile(loginUser.getMemberId(), requestDto);
        return ResponseWrapper.ok();
    }

    @PutMapping("/{itemId}/detail")
    public ResponseWrapper putItemDetail(@PathVariable Long itemId,
                                         @RequestBody ItemDetailRequestDto requestDto) {
        itemService.putItemDetail(itemId, requestDto);
        return ResponseWrapper.ok();
    }

    @PostMapping("/{itemId}/editor-infos")
    public ResponseWrapper postItemEditorInfo(@PathVariable Long itemId,
                                              ItemEditorInfoRequestDto requestDto) {
        itemService.insertItemEditorInfo(itemId, requestDto);
        return ResponseWrapper.ok();
    }

    @PostMapping("/{itemId}/editor-infos/{itemEditorInfoId")
    public ResponseWrapper postItemEditorInfo(@PathVariable Long itemId,
                                              @PathVariable Long itemEditorInfoId,
                                              ItemEditorInfoRequestDto requestDto) {
        return ResponseWrapper.ok();
    }

}
