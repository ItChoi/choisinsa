package com.mall.choisinsa.dto.request.member;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
public class MemberLoginRequestDto {
    @Size(min = 3, max = 10, message = "로그인 아이디 길이는 최소 3글자, 최대 10글자 입니다.")
    @NotBlank
    private String loginId;
    @Size(min = 6, max = 20, message = "비밀번호 길이는 최소 6글자, 최대 20글자 입니다.")
    @NotBlank
    private String password;
}
