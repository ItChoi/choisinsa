package com.mall.choisinsa.enumeration.item;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ItemStatus {
    READY("준비중"),
    INPUT("입력중"),
    ON_SALE("판매중"),
    SOLD_OUT("품절"),
    TEMP_SOLD_OUT("일시 품절"),
    END_SALE("판매 종료");

    private final String desc;

    public static boolean canPurchaseItemStatus(ItemStatus status) {
        return status == ON_SALE;
    }

    public static boolean isDisplayItemStatus(ItemStatus status) {
        return status == ON_SALE
                || status == SOLD_OUT
                || status == TEMP_SOLD_OUT
                || status == END_SALE;
    }
}
