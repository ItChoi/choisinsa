package core.dto.general;

import com.mall.choisinsa.security.dto.SecurityMostSimpleLoginUserDto;
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
