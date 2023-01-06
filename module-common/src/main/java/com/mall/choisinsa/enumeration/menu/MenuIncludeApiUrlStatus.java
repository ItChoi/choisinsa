package com.mall.choisinsa.enumeration.menu;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MenuIncludeApiUrlStatus {
    ACTIVE("활성"),
    INACTIVE("비활성");

    private final String desc;
}
