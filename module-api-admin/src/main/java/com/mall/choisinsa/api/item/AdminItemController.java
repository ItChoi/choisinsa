package com.mall.choisinsa.api.item;

import com.mall.choisinsa.dto.response.ResponseWrapper;
import com.mall.choisinsa.security.dto.SecurityMemberDto;
import core.dto.admin.request.item.ItemInsertRequestDto;
import core.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/items")
public class AdminItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseWrapper postItem(ItemInsertRequestDto requestDto,
                                    @AuthenticationPrincipal SecurityMemberDto loginUser) {
        itemService.saveItemBy(loginUser.getMemberId(), requestDto);
        return ResponseWrapper.ok();
    }

}
