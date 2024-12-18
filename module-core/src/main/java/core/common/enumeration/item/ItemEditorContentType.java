package core.common.enumeration.item;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ItemEditorContentType {
    IMAGE("이미지"),
    MARKUP_TEXT("마크업 포함 텍스트");

    private final String desc;
}
