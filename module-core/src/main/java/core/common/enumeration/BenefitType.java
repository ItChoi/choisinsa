package core.common.enumeration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BenefitType {
    NORMAL("일반"),
    DISCOUNT("할인"),
    PRESENT("선물"),
    PAY_BACK("페이백");

    private final String text;
}
