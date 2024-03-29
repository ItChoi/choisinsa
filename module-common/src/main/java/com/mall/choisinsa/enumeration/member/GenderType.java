package com.mall.choisinsa.enumeration.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Getter
@RequiredArgsConstructor
public enum GenderType {
    SECRET("비밀"),
    MALE("남성"),
    FEMALE("여성");

    private final String desc;

    public static GenderType from(String type) {
        if (StringUtils.isBlank(type)) {
            return null;
        }

        for (GenderType genderType : values()) {
            if (genderType.name().toLowerCase().equals(type)) {
                return genderType;
            }
        }

        return null;
    }
}
