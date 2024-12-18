package core.common.enumeration;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum DataActionType {
    INSERT,
    SELECT,
    UPDATE,
    DELETE,
    NONE;
}
