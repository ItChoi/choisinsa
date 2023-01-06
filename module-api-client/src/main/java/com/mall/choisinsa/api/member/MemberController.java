package com.mall.choisinsa.api.member;

import core.dto.request.member.MemberLoginRequestDto;
import core.dto.request.member.MemberRegisterRequestDto;
import com.mall.choisinsa.dto.response.ResponseWrapper;
import core.dto.response.member.MemberLoginResponseDto;
import com.mall.choisinsa.security.service.SecurityMemberService;
import core.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberController {
    //private final StockService stockService;
    //private final MemberValidator memberValidator;
    private final MemberService memberService;
    private final SecurityMemberService securityMemberService;


    // 이 컨트롤에 있는 메소드 호출마다 이 검증을 적용하게 된다. 메소드마다 검증 대상은 @Validated 추가
    /*@InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(memberValidator);
    }*/

    @PostMapping("/login")
    public ResponseWrapper login(@Validated @RequestBody MemberLoginRequestDto requestDto) {
        return ResponseWrapper.ok(
                new MemberLoginResponseDto(securityMemberService.login(requestDto.getLoginId(), requestDto.getPassword()))
        );
    }



    // @LoginMember -> ArgumentResolver 로직 완성 후 사용하기.
    @GetMapping("/{memberId}")
    public ResponseWrapper getMember(@PathVariable Long memberId) {
        return ResponseWrapper.ok(memberService.findMemberById(memberId));
    }


    @PostMapping
    public ResponseWrapper postMember(@Validated @RequestBody MemberRegisterRequestDto requestDto) {
        memberService.saveMember(requestDto);
        return ResponseWrapper.ok();
    }

    @GetMapping("/{loginId}/recommender")
    public ResponseWrapper canRecommendByLoginId(@PathVariable("loginId") String loginId) {
        return ResponseWrapper.ok(memberService.canRecommendByLoginId(loginId));
    }
}

