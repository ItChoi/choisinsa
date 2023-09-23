package com.mall.choisinsa.security.dto.menu;

import com.mall.choisinsa.enumeration.authority.AuthorityType;
import com.mall.choisinsa.enumeration.authority.UserDetailAuthorityType;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import com.mall.choisinsa.security.domain.*;
import com.mall.choisinsa.util.log.LogUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class SecurityAuthorityApplicationReadyDto {
    private AuthorityType authorityType;
    private Boolean isAdminAuthority;
    private Boolean isDisplay;
    private Boolean isUseMenuAuthority;
    private List<SecurityMenuDetailAuthorityApplicationReadyDto> menuDetailAuthorities;
    private SecurityMenuApplicationReadyDto menu;

    public SecurityAuthorityApplicationReadyDto(SecurityAuthority authority,
                                                SecurityAuthorityMenu authorityMenu) {

        if (authority.getType() != null) {
            this.authorityType = authority.getType();
            this.isAdminAuthority = authority.getIsAdminAuthority();
            this.isDisplay = authority.getIsDisplay();
            this.isUseMenuAuthority = authority.getIsUseMenuAuthority();
            Set<SecurityMenuDetailAuthority> userDetailAuthorities = authorityMenu.getUserDetailAuthorities();
            if (!CollectionUtils.isEmpty(userDetailAuthorities)) {
                this.menuDetailAuthorities = userDetailAuthorities.stream()
                        .map(SecurityMenuDetailAuthorityApplicationReadyDto::new)
                        .collect(Collectors.toList());
            }

            this.menu = new SecurityMenuApplicationReadyDto(authorityMenu.getMenu());
        }
    }


    @Getter
    public class SecurityMenuDetailAuthorityApplicationReadyDto {
        private UserDetailAuthorityType detailAuthorityType;

        public SecurityMenuDetailAuthorityApplicationReadyDto(SecurityMenuDetailAuthority menuDetailAuthority) {
            if (menuDetailAuthority != null) {
                this.detailAuthorityType = menuDetailAuthority.getType();
            }
        }
    }

    @Getter
    public class SecurityMenuApplicationReadyDto {
        private int depth;
        private int displayOrder;
        private String name;
        private List<SecurityMenuIncludeDetailApiUrlApplicationReadyDto> menuIncludeDetailApiUrls;

        public SecurityMenuApplicationReadyDto(SecurityMenu menu) {
            if (menu == null) {
                LogUtil.logErrorTypeAndOccurClass(ErrorType.NOT_EXISTS_REQUIRED_DATA, this.getClass());
            }

            this.depth = menu.getDepth();
            this.displayOrder = menu.getDisplayOrder();
            this.name = menu.getName();

            Set<SecurityMenuIncludeDetailApiUrl> menuIncludeDetailApiUrls = menu.getMenuIncludeDetailApiUrls();
            if (!CollectionUtils.isEmpty(menuIncludeDetailApiUrls)) {
                this.menuIncludeDetailApiUrls = menuIncludeDetailApiUrls.stream()
                        .map(SecurityMenuIncludeDetailApiUrlApplicationReadyDto::new)
                        .collect(Collectors.toList());
            }
        }

        @Setter
        @Getter
        public class SecurityMenuIncludeDetailApiUrlApplicationReadyDto {
            private String apiUrl;

            public SecurityMenuIncludeDetailApiUrlApplicationReadyDto(SecurityMenuIncludeDetailApiUrl menuIncludeDetailApiUrl) {
                this.apiUrl = menuIncludeDetailApiUrl.getApiUrl();
            }
        }
    }
}
