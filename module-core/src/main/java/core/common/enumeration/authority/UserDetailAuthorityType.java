package core.common.enumeration.authority;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserDetailAuthorityType {
    WRITE("쓰기 권한"),
    READ("읽기 권한"),
    UPLOAD("업로드 권한"),
    DOWNLOAD("쓰기 권한");

    private final String desc;
}
