package core.common.enumeration.authority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberCertificationType {

    EMAIL("이메일 인증"),
    PHONE("핸드폰 인증"),
    REFRESH_TOKEN("리프레시 토큰");

    private final String text;
}
