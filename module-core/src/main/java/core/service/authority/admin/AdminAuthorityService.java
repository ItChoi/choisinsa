package core.service.authority.admin;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.authority.AuthorityType;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import core.domain.authority.Authority;
import core.domain.authority.AuthorityMenu;
import core.domain.menu.Menu;
import core.dto.admin.request.authority.AdminAuthorityMenuInsertRequestDto;
import core.dto.admin.response.authority.AuthorityApplicationReadyDto;
import core.repository.authority.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@RequiredArgsConstructor
@Service
public class AdminAuthorityService {
    private final AuthorityRepository authorityRepository;
    //private final SecurityAuthorityService securityAuthorityService;
    private final AdminAuthorityMenuService adminAuthorityMenuService;


    @Transactional(readOnly = true)
    public Map<AuthorityType, List<AuthorityApplicationReadyDto>> initAuthorityMenuInMemoryData(boolean isAdminMenu) {
//        Map<AuthorityType, List<AuthorityApplicationReadyDto>> authorityTypeListMap = initAuthorityMenuInMemoryDataTest(isAdminMenu);
//        return convertValuesToAuthorityApplicationReadyDto(authorityTypeListMap);
        return initAuthorityMenuInMemoryDataTest(isAdminMenu);
    }

    @Transactional(readOnly = true)
    public List<AuthorityMenu> findAuthorityMenuAllBy(boolean isAdmin,
                                                      boolean isDisplay) {

        List<AuthorityType> authorityTypes = AuthorityType.getAuthoritiesTypeBy(isAdmin);
        return adminAuthorityMenuService.findAllByTypeInAndAuthority_IsUseMenuAuthority(authorityTypes, isDisplay);
    }

    @Transactional(readOnly = true)
    public Map<AuthorityType, List<AuthorityApplicationReadyDto>> initAuthorityMenuInMemoryDataTest(boolean isAdminMenu) {
        Map<AuthorityType, List<AuthorityApplicationReadyDto>> authMenusWithAuthType = initAuthMenuWithAuthType(isAdminMenu);

        List<AuthorityMenu> authorityMenus = findAuthorityMenuAllBy(isAdminMenu, true);
        authorityMenus.forEach(
                authorityMenu -> {
                    Authority authority = findByIdOrThrow(authorityMenu.getAuthorityId());
                    Menu menu = adminAuthorityMenuService.findMenuByMenuIdOrThrow(authorityMenu.getMenuId());
                    List<AuthorityApplicationReadyDto> authMenus = authMenusWithAuthType.get(authority.getType());
                    if (authMenus != null) {
                        authMenus.add(new AuthorityApplicationReadyDto(authority, authorityMenu, menu));
                    }
                }
        );

        return authMenusWithAuthType;
    }

    private Map<AuthorityType, List<AuthorityApplicationReadyDto>> initAuthMenuWithAuthType(boolean isAdminMenu) {
        Map<AuthorityType, List<AuthorityApplicationReadyDto>> authMenusWithAuthType = new HashMap<>();
        settingAuthMenuEmptyArrayWithAuthType(isAdminMenu, authMenusWithAuthType);
        return authMenusWithAuthType;
    }

    private void settingAuthMenuEmptyArrayWithAuthType(boolean isAdmin, Map authMenusWithAuthType) {
        if (authMenusWithAuthType.size() > 0) {
            authMenusWithAuthType.clear();
        }

        for (AuthorityType authType : AuthorityType.getAuthoritiesTypeBy(isAdmin)) {
            authMenusWithAuthType.put(authType, new ArrayList<>());
        }
    }

    @Transactional
    public void insertAuthorityMenus(Long authorityId,
                                     Collection<AdminAuthorityMenuInsertRequestDto> requestDtos) {
        validateAuthority(authorityId);
        adminAuthorityMenuService.insertAuthorityMenus(authorityId, requestDtos);
    }

    private void validateAuthority(Long authorityId) {
        if (authorityId == null) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_NULL);
        }

        findByIdOrThrow(authorityId);
    }

    @Transactional(readOnly = true)
    public Authority findByIdOrThrow(Long authorityId) {
        return authorityRepository.findById(authorityId)
                .orElseThrow(() -> new ErrorTypeAdviceException(ErrorType.NOT_FOUND_AUTHORITY));
    }
}
