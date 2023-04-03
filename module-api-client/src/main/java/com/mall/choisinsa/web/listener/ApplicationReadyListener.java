package com.mall.choisinsa.web.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("ApplicationReadyListener START");
        readyListener();
        initAuthDetailMenu();
        validateEndListener();
        log.info("ApplicationReadyListener END");
    }

    private void readyListener() {

    }

    private void initAuthDetailMenu() {

    }

    private void validateEndListener() {

    }

}
