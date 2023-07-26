package com.mall.choisinsa.api.item;

import com.mall.choisinsa.dto.response.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/items")
public class AdminItemController {

    /*@PostMapping
    public ResponseWrapper postItem(@Validated @RequestBody ItemInsertRequestDto requestDto) {
        return ResponseWrapper.ok();
    }*/

}
