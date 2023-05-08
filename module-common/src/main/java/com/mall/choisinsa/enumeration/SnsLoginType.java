package com.mall.choisinsa.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@RequiredArgsConstructor
public enum SnsLoginType {
    KAKAO("kakao", "카카오"),
    APPLIE("apple", "애플"),
    NAVER("naver", "네이버"),
    FACEBOOK("facebook", "페이스북"),
    INSTAGRAM("instagram", "인스타그램");

    private final String value;
    private final String desc;

    public static SnsLoginType matchToLowerCase(String value) {
        if (!StringUtils.hasText(value)) return null;

        for (SnsLoginType snsLoginType : values()) {
            if (snsLoginType.name().equalsIgnoreCase(value)) {
                return snsLoginType;
            }
        }

        return null;
    }
}
