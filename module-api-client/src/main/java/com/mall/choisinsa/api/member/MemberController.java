package com.mall.choisinsa.api.member;

import com.mall.choisinsa.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final StockService stockService;
    //private final MemberService



}

