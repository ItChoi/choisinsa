package com.mall.choisinsa.api.member;

import com.mall.choisinsa.domain.member.Member;
import com.mall.choisinsa.dto.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.dto.request.member.MemberLoginRequestDto;
import com.mall.choisinsa.dto.response.ResponseWrapper;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import com.mall.choisinsa.service.SecurityMemberService;
import com.mall.choisinsa.service.StockService;
import com.mall.choisinsa.web.validator.MemberValidator;
import com.mall.choisinsa.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final StockService stockService;
    private final MemberValidator memberValidator;
    private final MemberService memberService;
    private final SecurityMemberService securityMemberService;
    private final PasswordEncoder passwordEncoder;

    // 이 컨트롤에 있는 메소드 호출마다 이 검증을 적용하게 된다. 메소드마다 검증 대상은 @Validated 추가
    /*@InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(memberValidator);
    }*/

    @PostMapping("/login")
    public ResponseWrapper login(@Validated @RequestBody MemberLoginRequestDto requestDto) {
        return ResponseWrapper.ok(securityMemberService.login(requestDto.getLoginId(), requestDto.getPassword()));
    }



    // @LoginMember -> ArgumentResolver 로직 완성 후 사용하기.
    @GetMapping
    public ResponseWrapper getMember(Member member) {
        System.out.println("");
        throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        //return null;
    }


    @PostMapping
    public ResponseWrapper postMember() {

        return null;
    }

}

