package com.mall.choisinsa.enumeration.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TargetType {
    MALE("남성"),
    FEMALE("여성"),
    ETC("기타");

    private final String desc;
}
