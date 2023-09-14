package com.mall.choisinsa.api.item;

import core.service.stats.ItemStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/item-statses")
public class ItemStatsController {
    private final ItemStatsService itemStatsService;

}
