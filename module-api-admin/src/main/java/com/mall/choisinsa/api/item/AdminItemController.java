package com.mall.choisinsa.api.item;

import com.mall.choisinsa.dto.response.ResponseWrapper;
import com.mall.choisinsa.security.dto.SecurityMemberDto;
import core.dto.admin.request.item.AdminItemDetailRequestDto;
import core.dto.admin.request.item.AdminItemEditorInfoRequestDto;
import core.dto.admin.request.item.AdminItemRequestDto;
import core.dto.admin.request.item.AdminItemSearchRequestDto;
import core.service.item.AdminItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/items")
public class AdminItemController {

    private final AdminItemService adminItemService;

    // TODO: 테스트 코드 작성

    @GetMapping
    public ResponseWrapper getItems(AdminItemSearchRequestDto requestDto,
                                    @AuthenticationPrincipal SecurityMemberDto loginUser) {
        // TODO: 서비스 페이지 개발 후, 정책 정하고 작업 들어가기
        // 멤버 타입에 따라 적합한 아이템 뿌려주기
        //return ResponseWrapper.ok(itemService.findAllByMemberId(loginUser.getMemberId(), requestDto));
        return null;
    }

    @PostMapping
    public ResponseWrapper insertItemWithFile(AdminItemRequestDto requestDto,
                                              @AuthenticationPrincipal SecurityMemberDto loginUser) {
        adminItemService.insertItemWithFile(loginUser.getMemberId(), requestDto);
        return ResponseWrapper.ok();
    }

    @PostMapping("/{itemId}")
    public ResponseWrapper updateItemWithFile(@PathVariable Long itemId,
                                              AdminItemRequestDto requestDto,
                                              @AuthenticationPrincipal SecurityMemberDto loginUser) {
        adminItemService.updateItemWithFile(loginUser.getMemberId(), itemId, requestDto);
        return ResponseWrapper.ok();
    }

    // PUT -> 수정이든 등록이든 모든 데이터 다 보내기
    @PutMapping("/{itemId}/detail")
    public ResponseWrapper putItemDetail(@PathVariable Long itemId,
                                         @Validated @RequestBody AdminItemDetailRequestDto requestDto) {
        adminItemService.putItemDetail(itemId, requestDto);
        return ResponseWrapper.ok();
    }

    @PostMapping("/{itemId}/editor-infos")
    public ResponseWrapper insertItemEditorInfo(@PathVariable Long itemId,
                                                AdminItemEditorInfoRequestDto requestDto) {
        adminItemService.insertItemEditorInfo(itemId, requestDto);
        return ResponseWrapper.ok();
    }

    @PostMapping("/{itemId}/editor-infos/{itemEditorInfoId}")
    public ResponseWrapper updateItemEditorInfo(@PathVariable Long itemId,
                                                @PathVariable Long itemEditorInfoId,
                                                AdminItemEditorInfoRequestDto requestDto) {
        adminItemService.updateItemEditorInfo(itemId, itemEditorInfoId, requestDto);
        return ResponseWrapper.ok();
    }

    @DeleteMapping("/{itemId}/editor-infos")
    public ResponseWrapper deleteItemEditorInfos(@PathVariable Long itemId,
                                                 @RequestBody List<Long> itemEditorInfoIds) {
        adminItemService.deleteItemEditorInfoByIds(itemId, itemEditorInfoIds);
        return ResponseWrapper.ok();
    }

    @DeleteMapping("/{itemId}/editor-infos/{itemEditorInfoId}/contents")
    public ResponseWrapper deleteItemEditorContent(@PathVariable Long itemId,
                                                   @PathVariable Long itemEditorInfoId,
                                                   @RequestBody List<Long> itemEditorContentIds) {
        adminItemService.deleteItemEditorContentByIds(itemId, itemEditorInfoId, itemEditorContentIds);
        return ResponseWrapper.ok();
    }

}
