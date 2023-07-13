package com.mall.choisinsa.api.member;

import com.mall.choisinsa.dto.response.ResponseWrapper;
import com.mall.choisinsa.enumeration.SnsType;
import com.mall.choisinsa.security.service.SecurityMemberService;
import core.dto.request.member.*;
import core.dto.response.member.MemberLoginResponseDto;
import core.http.HttpCommunication;
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
    private final HttpCommunication httpCommunication;

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

    @PostMapping("/{snsType}")
    public ResponseWrapper postSnsMember(@PathVariable SnsType snsType,
                                         @Validated @RequestBody SnsMemberRegisterRequestDto requestDto) {

        return ResponseWrapper.ok(new MemberLoginResponseDto(memberService.saveMemberWithOauth2(snsType, requestDto)));
    }

    @GetMapping("/{loginId}/recommender")
    public ResponseWrapper canRecommendByLoginId(@PathVariable("loginId") String loginId) {
        return ResponseWrapper.ok(memberService.canRecommendByLoginId(loginId));
    }

    @PostMapping("/{email}/sns-link")
    public ResponseWrapper linkSnsWithEmail(@PathVariable String email,
                                            @Validated @RequestBody MemberSnsLinkRequestDto requestDto) {
        memberService.linkMemberWithSns(email, requestDto);
        return ResponseWrapper.ok();
    }

    @PostMapping("/{email}/sns-connect")
    public ResponseWrapper connectAccountWithSns(@PathVariable String email,
                                                 @Validated @RequestBody MemberSnsConnectRequestDto requestDto) {
        return ResponseWrapper.ok(memberService.connectAccountByEmail(email, requestDto));
    }

    @PostMapping("/{memberId}/sns-connect")
    public ResponseWrapper connectAccountWithSns(@PathVariable Long memberId,
                                                 @Validated @RequestBody MemberSnsConnectRequestDto requestDto) {
        return ResponseWrapper.ok(memberService.connectAccountByMemberId(memberId, requestDto));
    }

    @GetMapping("/{email}/is-available")
    public ResponseWrapper isAvailableEmail(@PathVariable String email) {
        return ResponseWrapper.ok(memberService.isExistEmail(email));
    }
}

