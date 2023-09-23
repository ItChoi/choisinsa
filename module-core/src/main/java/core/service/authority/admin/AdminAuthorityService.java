package core.service.authority.admin;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.authority.AuthorityType;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import com.mall.choisinsa.security.domain.SecurityAuthority;
import com.mall.choisinsa.security.domain.SecurityAuthorityMenu;
import com.mall.choisinsa.security.dto.menu.SecurityAuthorityApplicationReadyDto;
import com.mall.choisinsa.security.service.SecurityAuthorityService;
import core.domain.authority.Authority;
import core.dto.admin.request.authority.AdminAuthorityMenuInsertRequestDto;
import core.dto.admin.response.authority.AuthorityApplicationReadyDto;
import core.repository.authority.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminAuthorityService {
    private final AuthorityRepository authorityRepository;
    //private final SecurityAuthorityService securityAuthorityService;
    private final AdminAuthorityMenuService adminAuthorityMenuService;


    @Transactional(readOnly = true)
    public Map<AuthorityType, List<AuthorityApplicationReadyDto>> initAuthorityMenuInMemoryData(boolean isAdminMenu) {
        Map<AuthorityType, List<SecurityAuthorityApplicationReadyDto>> authorityTypeListMap = initAuthorityMenuInMemoryDataTest(isAdminMenu);
        return convertValuesToAuthorityApplicationReadyDto(authorityTypeListMap);
    }

    @Transactional(readOnly = true)
    public List<SecurityAuthorityMenu> findAuthorityMenuAllBy(boolean isDisplay,
                                                              boolean isUseMenuAuthority) {

        return adminAuthorityMenuService.findAllByAuthority_IsDisplayAndAuthority_IsUseMenuAuthority(isDisplay, isUseMenuAuthority);
    }

    @Transactional(readOnly = true)
    public Map<AuthorityType, List<SecurityAuthorityApplicationReadyDto>> initAuthorityMenuInMemoryDataTest(boolean isAdminMenu) {
        System.out.println("asdasdasd");
        Map<AuthorityType, List<SecurityAuthorityApplicationReadyDto>> authMenusWithAuthType = new HashMap<>();
        /*settingAuthMenuEmptyArrayWithAuthType(isAdminMenu, authMenusWithAuthType);

        findAuthorityMenuAllBy(true, true).forEach(
                authorityMenu -> {
                    SecurityAuthority authority = authorityMenu.getAuthority();
                    List<SecurityAuthorityApplicationReadyDto> authMenus = authMenusWithAuthType.get(authority.getType());
                    if (authMenus != null) {
                        authMenus.add(new SecurityAuthorityApplicationReadyDto(authority, authorityMenu));
                    }
                }
        );*/

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

    private Map<AuthorityType, List<AuthorityApplicationReadyDto>> convertValuesToAuthorityApplicationReadyDto(Map<AuthorityType, List<SecurityAuthorityApplicationReadyDto>> authorityTypeListMap) {
        return authorityTypeListMap.entrySet().stream()
                .flatMap(entry -> entry.getValue().stream()
                        .map(AuthorityApplicationReadyDto::new)
                )
                .collect(Collectors.groupingBy(
                        AuthorityApplicationReadyDto::getAuthorityType,
                        Collectors.toList())
                );
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
