package core.dto.general;

import core.dto.SecurityMostSimpleLoginUserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto {
    private Long memberId;
    private String loginId;

    public LoginUserDto(SecurityMostSimpleLoginUserDto securityMostSimpleLoginUserDto) {
        this.memberId = securityMostSimpleLoginUserDto.getMemberId();
        this.loginId = securityMostSimpleLoginUserDto.getLoginId();
    }
}
