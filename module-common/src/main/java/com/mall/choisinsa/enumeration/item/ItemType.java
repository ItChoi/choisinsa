package com.mall.choisinsa.enumeration.item;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ItemType {
    TOP("상의"),
    BOTTOM("바지"),
    DRESS("드레스"),
    ACCESSORY("액세사리"),
    JEWELRY("반지/귀걸이/목걸이 등"),
    SCARF("스카프/머플러");

    private final String desc;
}
