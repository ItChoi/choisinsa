package core.dto.general;

import core.dto.SecurityMemberDto;
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

    public LoginUserDto(SecurityMemberDto securityMemberDto) {
        this.memberId = securityMemberDto.getMemberId();
        this.loginId = securityMemberDto.getLoginId();
    }
}
