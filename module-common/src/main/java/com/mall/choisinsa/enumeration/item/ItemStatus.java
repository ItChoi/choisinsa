package com.mall.choisinsa.enumeration.item;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ItemStatus {
    READY("준비중"),
    INPUT("입력중"),
    ON_SALE("판매중"),
    SOLD_OUT("품절"),
    TEMP_SOLD_OUT("일시 품절");

    private final String desc;
}
