package com.mall.choisinsa.api.member;

import com.mall.choisinsa.dto.response.ResponseWrapper;
import core.dto.client.request.member.MemberLoginRequestDto;
import core.dto.client.response.member.MemberLoginResponseDto;
import core.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/members")
public class AdminMemberController {

    private final MemberService memberService;

    //private final SecurityMemberService securityMemberService;

    @PostMapping("/login")
    public ResponseWrapper login(@Validated @RequestBody MemberLoginRequestDto requestDto) {
        return ResponseWrapper.ok(
                new MemberLoginResponseDto(memberService.login(requestDto))
        );
    }

}
