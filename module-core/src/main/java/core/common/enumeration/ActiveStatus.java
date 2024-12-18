package core.common.enumeration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ActiveStatus {
    ACTIVE("활성"),
    INACTIVE("비활성");

    private final String text;
}
