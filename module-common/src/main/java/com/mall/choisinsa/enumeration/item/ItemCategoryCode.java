package com.mall.choisinsa.enumeration.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ItemCategoryCode {
    /*TOP("상의"),
    BOTTOM("바지"),
    DRESS("드레스"),
    ACCESSORY("액세사리"),
    JEWELRY("반지/귀걸이/목걸이 등"),
    SCARF("스카프/머플러");*/

    TOP("상의"),
    BOTTOM("하의"),
    SHORT_SLEEVE_T_SHIRT("반소매 티셔츠"),
    LONG_SLEEVE_T_SHIRT("긴소매 티셔츠"),
    SHIRT_BLOUSE("셔츠/블라우스");


    private final String desc;
}
