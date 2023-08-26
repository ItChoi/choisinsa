package com.mall.choisinsa.enumeration.member;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MemberType {
    SUPER("최고 내부 관리자"),
    ADMIN("내부 관리자"),
    MEMBER("회원"),
    BRAND_ADMIN("브랜드 외부 관리자"),
    COMPANY_ADMIN("회사 외부 관리자");

    private final String desc;

    public static boolean isInternalAdmin(MemberType memberType) {
        return memberType == ADMIN || memberType == SUPER;
    }

    public static boolean isExternalAdmin(MemberType memberType) {
        return memberType == BRAND_ADMIN || memberType == COMPANY_ADMIN;
    }
}
