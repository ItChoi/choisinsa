package com.mall.choisinsa.enumeration.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthorityType {
    SUPER("슈퍼 관리자"),
    ADMIN("관리자"),
    MEMBER("이용자");

    private final String text;
}
