package com.mall.choisinsa.enumeration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EntryFeeType {
    FREE("무료"),
    PAID("유료"),
    CONDITION_FREE("조건 무료");

    private final String text;
}
