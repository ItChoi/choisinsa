package com.mall.choisinsa.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

@Getter
@RequiredArgsConstructor
public enum SnsType {
    KAKAO("kakao", "카카오"),
    APPLIE("apple", "애플"),
    NAVER("naver", "네이버"),
    FACEBOOK("facebook", "페이스북"),
    INSTAGRAM("instagram", "인스타그램");

    private final String value;
    private final String desc;

    public static SnsType matchToLowerCase(String value) {
        if (!StringUtils.hasText(value)) return null;

        for (SnsType snsType : values()) {
            if (snsType.name().equalsIgnoreCase(value)) {
                return snsType;
            }
        }

        return null;
    }
}
