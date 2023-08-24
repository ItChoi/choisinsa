package com.mall.choisinsa.enumeration.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ItemOptionType {
    SIZE("사이즈"),
    COLOR("색상");

    private final String desc;
}
