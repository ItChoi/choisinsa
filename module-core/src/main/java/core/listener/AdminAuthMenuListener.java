package core.listener;

import com.mall.choisinsa.security.service.SecurityAuthorityService;
import core.service.authority.AuthorityService;
import core.service.authority.admin.AdminAuthorityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

@Profile("admin")
@Slf4j
@Component
public class AdminAuthMenuListener {
    private AdminAuthorityService authorityService;

    public AdminAuthMenuListener(@Lazy AdminAuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @PostPersist
    public void insert(Object o) {
        changeMenuDetailAuthWithAdminAuthType();
    }

    @PostUpdate
    public void update(Object o) {
        changeMenuDetailAuthWithAdminAuthType();
    }


    @PostRemove
    public void delete(Object o) {
        changeMenuDetailAuthWithAdminAuthType();
    }

    private void changeMenuDetailAuthWithAdminAuthType() {
        ApplicationReadyListener.menuDetailAuthWithAdminAuthType = authorityService.initAuthorityMenuInMemoryData(true);
    }

}
