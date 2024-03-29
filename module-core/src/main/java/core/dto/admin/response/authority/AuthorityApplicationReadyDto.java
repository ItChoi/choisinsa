package core.dto.admin.response.authority;

import com.mall.choisinsa.enumeration.authority.AuthorityType;
import com.mall.choisinsa.enumeration.authority.UserDetailAuthorityType;
import core.domain.authority.Authority;
import core.domain.authority.AuthorityMenu;
import core.domain.authority.MenuDetailAuthority;
import core.domain.menu.Menu;
import core.domain.menu.MenuIncludeDetailApiUrl;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class AuthorityApplicationReadyDto {
    private AuthorityType authorityType;
    private Boolean isAdminAuthority;
    private Boolean isDisplay;
    private Boolean isUseMenuAuthority;
    private List<MenuDetailAuthorityApplicationReadyDto> menuDetailAuthorities;
    private MenuApplicationReadyDto menu;

    public AuthorityApplicationReadyDto(Authority authority,
                                        AuthorityMenu authorityMenu,
                                        Menu menu) {

        if (authority.getType() != null) {
            this.authorityType = authority.getType();
            this.isAdminAuthority = authority.getIsAdminAuthority();
            this.isDisplay = authority.getIsDisplay();
            this.isUseMenuAuthority = authority.getIsUseMenuAuthority();
            Set<MenuDetailAuthority> userDetailAuthorities = authorityMenu.getUserDetailAuthorities();
            if (!CollectionUtils.isEmpty(userDetailAuthorities)) {
                this.menuDetailAuthorities = userDetailAuthorities.stream()
                        .map(MenuDetailAuthorityApplicationReadyDto::new)
                        .collect(Collectors.toList());
            }

            this.menu = new MenuApplicationReadyDto(menu);
        }
    }

    public AuthorityApplicationReadyDto(AuthorityApplicationReadyDto authorityApplicationReadyDto) {
        this.authorityType = authorityApplicationReadyDto.getAuthorityType();
        this.isAdminAuthority = authorityApplicationReadyDto.getIsAdminAuthority();
        this.isDisplay = authorityApplicationReadyDto.getIsDisplay();
        this.isUseMenuAuthority = authorityApplicationReadyDto.getIsUseMenuAuthority();
        List<MenuDetailAuthorityApplicationReadyDto> menuDetailAuthorities = authorityApplicationReadyDto.getMenuDetailAuthorities();
        if (!CollectionUtils.isEmpty(menuDetailAuthorities)) {
            this.menuDetailAuthorities = menuDetailAuthorities.stream()
                    .map(MenuDetailAuthorityApplicationReadyDto::new)
                    .collect(Collectors.toList());
        }
        this.menu = new MenuApplicationReadyDto(authorityApplicationReadyDto.getMenu());
    }



    @Getter
    public class MenuDetailAuthorityApplicationReadyDto {
        private UserDetailAuthorityType detailAuthorityType;

        public MenuDetailAuthorityApplicationReadyDto(MenuDetailAuthority menuDetailAuthority) {
            if (menuDetailAuthority != null) {
                this.detailAuthorityType = menuDetailAuthority.getType();
            }
        }

        public MenuDetailAuthorityApplicationReadyDto(AuthorityApplicationReadyDto.MenuDetailAuthorityApplicationReadyDto menuDetailAuthorityApplicationReadyDto) {
            this.detailAuthorityType = menuDetailAuthorityApplicationReadyDto.getDetailAuthorityType();
        }
    }

    @Getter
    public class MenuApplicationReadyDto {
        private int depth;
        private int displayOrder;
        private String name;
        private List<MenuIncludeDetailApiUrlApplicationReadyDto> menuIncludeDetailApiUrls;

        public MenuApplicationReadyDto(Menu menu) {
            this.depth = menu.getDepth();
            this.displayOrder = menu.getDisplayOrder();
            this.name = menu.getName();

            Set<MenuIncludeDetailApiUrl> menuIncludeDetailApiUrls = menu.getMenuIncludeDetailApiUrls();
            if (!CollectionUtils.isEmpty(menuIncludeDetailApiUrls)) {
                this.menuIncludeDetailApiUrls = menuIncludeDetailApiUrls.stream()
                        .map(MenuIncludeDetailApiUrlApplicationReadyDto::new)
                        .collect(Collectors.toList());
            }
        }

        public MenuApplicationReadyDto(AuthorityApplicationReadyDto.MenuApplicationReadyDto menu) {
            this.depth = menu.getDepth();
            this.displayOrder = menu.getDisplayOrder();
            this.name = menu.getName();
            this.menuIncludeDetailApiUrls = menu.getMenuIncludeDetailApiUrls().stream()
                    .map(MenuIncludeDetailApiUrlApplicationReadyDto::new)
                    .collect(Collectors.toList());
        }

        @Setter
        @Getter
        public class MenuIncludeDetailApiUrlApplicationReadyDto {
            private String apiUrl;

            public MenuIncludeDetailApiUrlApplicationReadyDto(MenuIncludeDetailApiUrl menuIncludeDetailApiUrl) {
                this.apiUrl = menuIncludeDetailApiUrl.getApiUrl();
            }

            public MenuIncludeDetailApiUrlApplicationReadyDto(AuthorityApplicationReadyDto.MenuApplicationReadyDto.MenuIncludeDetailApiUrlApplicationReadyDto menuIncludeDetailApiUrl) {
                this.apiUrl = menuIncludeDetailApiUrl.getApiUrl();
            }
        }
    }
}
