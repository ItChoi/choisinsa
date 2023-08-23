package com.mall.choisinsa.security.service;

import com.mall.choisinsa.enumeration.authority.AuthorityType;
import com.mall.choisinsa.security.domain.SecurityAuthority;
import com.mall.choisinsa.security.domain.SecurityAuthorityMenu;
import com.mall.choisinsa.security.dto.menu.AuthorityApplicationReadyDto;
import com.mall.choisinsa.security.repository.SecurityAuthorityMenuRepository;
import com.mall.choisinsa.security.repository.SecurityAuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class SecurityAuthorityService {
    private final SecurityAuthorityRepository authorityRepository;
    private final SecurityAuthorityMenuRepository authorityMenuRepository;

    @Transactional(readOnly = true)
    public List<SecurityAuthorityMenu> findAuthorityMenuAllBy(boolean isDisplay,
                                                              boolean isUseMenuAuthority) {

        return authorityMenuRepository.findAllByAuthority_IsDisplayAndAuthority_IsUseMenuAuthority(isDisplay, isUseMenuAuthority);
    }

    @Transactional(readOnly = true)
    public Map<AuthorityType, List<AuthorityApplicationReadyDto>> initAuthorityMenuInMemoryData(boolean isAdminMenu) {

        Map<AuthorityType, List<AuthorityApplicationReadyDto>> authMenusWithAuthType = new HashMap<>();
        settingAuthMenuEmptyArrayWithAuthType(isAdminMenu, authMenusWithAuthType);

        findAuthorityMenuAllBy(true, true).forEach(
                authorityMenu -> {
                    SecurityAuthority authority = authorityMenu.getAuthority();
                    List<AuthorityApplicationReadyDto> authMenus = authMenusWithAuthType.get(authority.getType());
                    if (authMenus != null) {
                        authMenus.add(new AuthorityApplicationReadyDto(authority, authorityMenu));
                    }
                }
        );

        return authMenusWithAuthType;
    }

    private void settingAuthMenuEmptyArrayWithAuthType(boolean isAdmin, Map authMenusWithAuthType) {
        if (authMenusWithAuthType.size() > 0) {
            authMenusWithAuthType.clear();
        }

        for (AuthorityType authType : AuthorityType.getAuthorityTypeBy(isAdmin)) {
            authMenusWithAuthType.put(authType, new ArrayList<>());
        }
    }
}
