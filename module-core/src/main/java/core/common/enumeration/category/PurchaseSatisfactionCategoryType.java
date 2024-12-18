package core.common.enumeration.category;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PurchaseSatisfactionCategoryType {
    SIZE("사이즈"),
    BRIGHTNESS("밝기"),
    COLOR("색상"),
    THICKNESS("두께");

    private final String desc;
}
