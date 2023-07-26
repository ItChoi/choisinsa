package com.mall.choisinsa.api.authority;

import com.mall.choisinsa.dto.response.ResponseWrapper;
import com.mall.choisinsa.enumeration.member.MemberType;
import com.mall.choisinsa.security.service.SecurityMemberService;
import core.dto.request.member.MemberLoginRequestDto;
import core.dto.response.member.MemberLoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminAuthorityController {

    private final SecurityMemberService securityMemberService;

    @PostMapping("/login")
    public ResponseWrapper login(@Validated @RequestBody MemberLoginRequestDto requestDto) {
        return ResponseWrapper.ok(
                new MemberLoginResponseDto(securityMemberService.login(
                        MemberType.ADMIN,
                        requestDto.getLoginId(),
                        requestDto.getPassword())
                )
        );
    }

}
