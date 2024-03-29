package core.dto;

import lombok.Getter;

@Getter
public class SecurityMostSimpleLoginUserDto {
    private Long memberId;
    private String loginId;

    public SecurityMostSimpleLoginUserDto(SecurityMemberDto securityMemberDto) {
        this.memberId = securityMemberDto.getMemberId();
        this.loginId = securityMemberDto.getLoginId();
    }
}
