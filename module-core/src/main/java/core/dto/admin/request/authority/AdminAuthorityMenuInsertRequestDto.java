package core.dto.admin.request.authority;


import core.domain.authority.AuthorityMenu;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class AdminAuthorityMenuInsertRequestDto {
    @NotNull
    private Long menuId;
    @NotNull
    private Boolean isUse;

    public AuthorityMenu toAuthorityMenu(Long authorityId) {
        return AuthorityMenu.builder()
                .authorityId(authorityId)
                .menuId(this.menuId)
                .isUse(this.isUse)
                .build();
    }
}

