package com.mall.choisinsa.enumeration.event;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum EventMethod {
    PERIOD("기간 타입"),
    ALWAYS("무제한 타입"),
    RECOMMENDATION("추천 타입"),
    ADVERTISING("광고 타입"),
    FCFA("선착순 타입"),
    DESIGNATED_HOUR("지정된 시간"),
    DESIGNATED_DAY("지정된 요일"),
    DESIGNATED_DAY_HOUR("지정된 요일, 지정된 시간");

    private final String text;
}
