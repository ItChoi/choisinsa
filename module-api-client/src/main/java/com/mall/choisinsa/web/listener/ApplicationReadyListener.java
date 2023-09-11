package com.mall.choisinsa.web.listener;

import core.dto.client.response.item.ItemCountAllPerCategoryApplicationReadyDto;
import core.service.item.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {

    private final ItemService itemService;

    //public static Map<AuthorityType, List<AuthorityApplicationReadyDto>> menuDetailAuthWithAdminAuthType = new HashMap<>();
    public static List<ItemCountAllPerCategoryApplicationReadyDto> allItemCountPerCategory;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("ApplicationReadyListener START");
        readyListener();
        initAuthDetailMenu();
        initItemCountAllPerCategory();
        validateEndListener();
        log.info("ApplicationReadyListener END");
    }

    private void initItemCountAllPerCategory() {
        // 카테고리별 아이템 개수
        allItemCountPerCategory = itemService.findItemCountAllPerCategoryApplicationReadyDtoAll();
    }

    private void readyListener() {

    }

    private void initAuthDetailMenu() {

    }

    private void validateEndListener() {

    }

}
