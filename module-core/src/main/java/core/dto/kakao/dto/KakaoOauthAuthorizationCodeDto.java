package core.dto.kakao.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoOauthAuthorizationCodeDto {
    private String code;
    private String state;
    private String error;
    private String error_description;
}
