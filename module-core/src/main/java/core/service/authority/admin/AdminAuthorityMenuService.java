package core.service.authority.admin;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.authority.AuthorityType;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import com.mall.choisinsa.security.domain.SecurityAuthorityMenu;
import core.annotation.AuthMenuDirtyCheckListener;
import core.domain.authority.AuthorityMenu;
import core.domain.menu.Menu;
import core.dto.admin.request.authority.AdminAuthorityMenuInsertRequestDto;
import core.repository.authority.AuthorityMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminAuthorityMenuService {

    private final AuthorityMenuRepository adminAuthorityMenuRepository;
    private final AdminMenuService adminMenuService;

    @AuthMenuDirtyCheckListener
    @Transactional
    public void insertAuthorityMenus(Long authorityId,
                                     Collection<AdminAuthorityMenuInsertRequestDto> requestDtos) {
        validateAuthorityMenuForInsert(authorityId, requestDtos);

        List<AuthorityMenu> targetAuthorityMenus = extractNotExistsAuthMenusForInsert(authorityId, requestDtos);
        if (!CollectionUtils.isEmpty(targetAuthorityMenus)) {
            adminAuthorityMenuRepository.saveAll(targetAuthorityMenus);
        }
    }

    private List<AuthorityMenu> extractNotExistsAuthMenusForInsert(Long authorityId, Collection<AdminAuthorityMenuInsertRequestDto> requestDtos) {
        Map<Long, AuthorityMenu> authMenuWithMenuId = adminAuthorityMenuRepository.findAllByAuthorityIdAndMenuIdIn(authorityId, extractMenuIds(requestDtos)).stream()
                .collect(Collectors.toMap(AuthorityMenu::getMenuId, Function.identity()));

        return requestDtos.stream()
                .filter(dto -> authMenuWithMenuId.get(dto.getMenuId()) == null)
                .map(dto -> dto.toAuthorityMenu(authorityId))
                .collect(Collectors.toList());
    }

    private List<Long> extractMenuIds(Collection<AdminAuthorityMenuInsertRequestDto> requestDtos) {
        if (CollectionUtils.isEmpty(requestDtos)) {
            return new ArrayList<>();
        }

        return requestDtos.stream()
                .map(AdminAuthorityMenuInsertRequestDto::getMenuId)
                .collect(Collectors.toList());
    }

    private void validateAuthorityMenuForInsert(Long authorityId,
                                                Collection<AdminAuthorityMenuInsertRequestDto> requestDtos) {
        if (authorityId == null) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_NULL);
        }

        List<Long> menuIds = extractMenuIds(requestDtos);
        if (CollectionUtils.isEmpty(menuIds)) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_EMPTY);
        }

        if (!adminMenuService.isMatchMenuCountWithInputAndOrigin(menuIds)) {
            throw new ErrorTypeAdviceException(ErrorType.MISMATCH_DATA_SIZE);
        }
    }

    @Transactional(readOnly = true)
    //public List<SecurityAuthorityMenu> findAllByTypeInAndAuthority_IsUseMenuAuthority(Collection<AuthorityType> types,
    public List<AuthorityMenu> findAllByTypeInAndAuthority_IsUseMenuAuthority(Collection<AuthorityType> types,
                                                                                      boolean isDisplay) {
        return adminAuthorityMenuRepository.findAllByAuthority_TypeInAndAuthority_IsDisplay(types, isDisplay);
    }

    @Transactional(readOnly = true)
    public Menu findMenuByMenuIdOrThrow(Long menuId) {
        return adminMenuService.findByIdOrThrow(menuId);
    }
}
