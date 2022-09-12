package com.mall.choisinsa.enumeration.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GenderType {
    SECRET("비밀"),
    MALE("남성"),
    FEMALE("여성");

    private final String text;
}
