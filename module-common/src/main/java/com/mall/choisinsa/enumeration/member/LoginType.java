package com.mall.choisinsa.enumeration.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoginType {
    DEFAULT("site"),
    SITE("site"),
    KAKAO("kakao"),
    APPLE("apple"),
    NAVER("naver"),
    FACEBOOK("facebook");

    private final String text;
}
