package core.dto.client.response.oauth2;

import lombok.Getter;

@Getter
public class Oauth2ResponseDto {
    private Boolean hasSnsLogin; // 로그인 성공
    private Boolean isAlreadyExistEmail; // 이메일만 존재, 연동 여부 이용자에게 체크 필요
    private Oauth2UserResponseDto memberInfo;

    public Oauth2ResponseDto(Boolean hasSnsLogin,
                             Boolean isAlreadyExistEmail,
                             Oauth2UserResponseDto memberInfo) {

        this.hasSnsLogin = hasSnsLogin;
        this.isAlreadyExistEmail = isAlreadyExistEmail;
        this.memberInfo = memberInfo;
    }
}
