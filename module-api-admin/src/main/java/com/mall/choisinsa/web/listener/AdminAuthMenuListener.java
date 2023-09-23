/*
package com.mall.choisinsa.web.listener;

import core.service.authority.admin.AdminAuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

@Slf4j
@Component
public class AdminAuthMenuListener {
    private AdminAuthorityService adminAuthorityService;

    public AdminAuthMenuListener(@Lazy AdminAuthorityService adminAuthorityService) {
        this.adminAuthorityService = adminAuthorityService;
    }

    @PostPersist
    public void insert(Object o) {
        System.out.println("test1");
        changeMenuDetailAuthWithAdminAuthType();
    }

    @PostUpdate
    public void update(Object o) {
        System.out.println("test2");
        changeMenuDetailAuthWithAdminAuthType();
    }


    @PostRemove
    public void delete(Object o) {
        System.out.println("test3");
        changeMenuDetailAuthWithAdminAuthType();
    }

    private void changeMenuDetailAuthWithAdminAuthType() {
        ApplicationReadyListener.menuDetailAuthWithAdminAuthType = adminAuthorityService.initAuthorityMenuInMemoryData(true);
    }

}
*/
