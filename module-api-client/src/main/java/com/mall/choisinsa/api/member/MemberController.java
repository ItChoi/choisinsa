package com.mall.choisinsa.api.member;

import com.mall.choisinsa.annotation.LoginMember;
import com.mall.choisinsa.domain.member.Member;
import com.mall.choisinsa.dto.response.ResponseWrapper;
import com.mall.choisinsa.service.StockService;
import com.mall.choisinsa.validator.MemberValidator;
import com.mall.choisinsa.service.member.MemberService;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final StockService stockService;
    private final MemberValidator memberValidator;
    private final MemberService memberService;


    // 이 컨트롤에 있는 메소드 호출마다 이 검증을 적용하게 된다. 메소드마다 검증 대상은 @Validated 추가
    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(memberValidator);
    }

    // @LoginMember -> ArgumentResolver 로직 완성 후 사용하기.
    @GetMapping
    public ResponseWrapper getMember(Member member) {
        System.out.println("");
        throw new IllegalArgumentException();
        //return null;
    }


    @PostMapping
    public ResponseWrapper postMember() {

        return null;
    }

}

