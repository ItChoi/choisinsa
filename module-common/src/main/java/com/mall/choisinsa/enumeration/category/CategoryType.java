package com.mall.choisinsa.enumeration.category;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * CategoryType은 Category 테이블의 categoryType을 말한다.
 * ex) CategoryType = BRAND -> 1뎁스 2뎁스 모두 BRAND, code로 구분한다.
 */
@Getter
@RequiredArgsConstructor
public enum CategoryType {
    EVENT("event", "이벤트");

    private final String code;
    private final String categoryName;
}
