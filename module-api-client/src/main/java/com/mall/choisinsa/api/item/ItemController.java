package com.mall.choisinsa.api.item;

import core.dto.item.ItemRequestDto;
import com.mall.choisinsa.dto.response.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/items")
public class ItemController {

    @PostMapping
    public ResponseWrapper test(@RequestBody ItemRequestDto requestDto) {
        System.out.println("test:::");
        return null;
    }

}
