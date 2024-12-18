package core.common.enumeration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ConditionType {
    PARTICIPATION("참여"),
    SHARING("공유"),
    PAY("일정 금액 이상");

    private final String text;
}
