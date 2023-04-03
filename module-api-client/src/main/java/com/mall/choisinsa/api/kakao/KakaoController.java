package com.mall.choisinsa.api.kakao;

import com.mall.choisinsa.dto.response.ResponseWrapper;
import core.dto.kakao.dto.KakaoOauthAuthorizationCodeDto;
import core.dto.kakao.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/kakao")
public class KakaoController {

    private final KakaoService kakaoService;

    // TODO: 카카오 승인 페이지 - 프론트 시작할 때 주석 풀 것
    /*@GetMapping("/oauth/authorize")
    public ResponseWrapper getAuthorizeCode(KakaoAuthorizeCodeRequestDto requestDto) {
        return ResponseWrapper.ok(kakaoService.getAuthorizeCode(requestDto));
    }*/

    @GetMapping("/oauth/authorization-code")
    public ResponseWrapper getKakaoOauthAuthorizationCode(KakaoOauthAuthorizationCodeDto requestDto) {
        return ResponseWrapper.ok(kakaoService.authorizeOauth(requestDto));
    }

    /*@PostMapping("/oauth/token")
    public ResponseWrapper getKakaoOauthToken(@RequestBody KakaoOauthTokenDto requestDto) {
        System.out.println("asdasd");
        return null;
    }*/

}