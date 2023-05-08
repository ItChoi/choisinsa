package core.dto.request.oauth2;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Oauth2LoginRequestDto {
    // 카카오 로그인 요청 정보 START
    private String code;
    private String state;
    private String error;
    private String error_description;
    // 카카오 로그인 요청 정보 END
}
