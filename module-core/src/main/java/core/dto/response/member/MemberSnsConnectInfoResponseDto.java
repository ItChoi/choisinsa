package core.dto.response.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class MemberSnsConnectInfoResponseDto {
    @Setter
    private Boolean hasSnsLogin; // 로그인 성공
    @Setter
    private Boolean isAlreadyExistEmail; // 이메일만 존재, 연동 여부 이용자에게 체크 필요
}
