package com.mall.choisinsa.web.aop;

import com.mall.choisinsa.web.listener.ApplicationReadyListener;
import core.service.authority.admin.AdminAuthorityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class AuthorityMenuAspect {

    private final AdminAuthorityService adminAuthorityService;

    @Pointcut("@annotation(core.annotation.AuthMenuDirtyCheckListener)")
    public void authMenuDirtyCheckListener() {

    }

    @Around("authMenuDirtyCheckListener()")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            String executionLocation = joinPoint.getSignature().toShortString();
            log.info("[{} ASPECT] {}", this.getClass(), executionLocation);
            //로직 호출
            Object result = joinPoint.proceed();

            ApplicationReadyListener.menuDetailAuthWithAdminAuthType = adminAuthorityService.initAuthorityMenuInMemoryData(true);
            return result;
        } catch (Exception e) {
            throw e;
        }
    }
}

