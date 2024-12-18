package core.common.enumeration.menu;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MenuType {
    SERVICE("서비스 페이지"),
    ADMIN("관리자 페이지");

    private final String desc;

}
