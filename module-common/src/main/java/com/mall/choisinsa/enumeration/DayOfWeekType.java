package com.mall.choisinsa.enumeration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DayOfWeekType {
    SUNDAY("일요일", 0),
    MONDAY("월요일", 1),
    TUESDAY("화요일", 2),
    WEDNESDAY("수요일", 3),
    THURSDAY("목요일", 4),
    FRIDAY("금요일", 5),
    SATURDAY("토요일", 6);

    private final String text;
    private final int value;
}
