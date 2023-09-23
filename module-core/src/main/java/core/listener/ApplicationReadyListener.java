package core.listener;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.authority.AuthorityType;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import com.mall.choisinsa.security.dto.menu.SecurityAuthorityApplicationReadyDto;
import com.mall.choisinsa.security.service.SecurityAuthorityService;
import core.dto.admin.response.authority.AuthorityApplicationReadyDto;
import core.service.authority.admin.AdminAuthorityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Profile("admin")
@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {

    private final AdminAuthorityService authorityService;

    public static Map<AuthorityType, List<AuthorityApplicationReadyDto>> menuDetailAuthWithAdminAuthType = new HashMap<>();

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("ApplicationReadyListener START");
        readyListener();


        validateEndListener();
        log.info("ApplicationReadyListener END");
    }

    private void readyListener() {
        initAuthorityMenuInMemoryData();

    }

    private void validateEndListener() {
        for (AuthorityType authorityType : menuDetailAuthWithAdminAuthType.keySet()) {
            if (!AuthorityType.isRightAuthority(authorityType, true)) {
                log.error("ERROR: [{}] 올바르지 않은 관리자 권한입니다.", authorityType);
                throw new ErrorTypeAdviceException(ErrorType.MISMATCH_AUTHORITY);
            }
        }
    }

    private void initAuthorityMenuInMemoryData() {
        menuDetailAuthWithAdminAuthType = authorityService.initAuthorityMenuInMemoryData(true);
    }
}
