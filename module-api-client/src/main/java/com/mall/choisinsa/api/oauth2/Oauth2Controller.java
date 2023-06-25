package com.mall.choisinsa.api.oauth2;

import com.mall.choisinsa.dto.response.ResponseWrapper;
import com.mall.choisinsa.enumeration.SnsLoginType;
import core.dto.request.oauth2.Oauth2LoginRequestDto;
import core.service.oauth2.Oauth2Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class Oauth2Controller {

    //private final KakaoService kakaoService;

    private final Oauth2Service oauth2Service;

    // TODO: 카카오 승인 페이지 - 프론트 시작할 때 주석 풀 것
    /*@GetMapping("/oauth/authorize")
    public ResponseWrapper getAuthorizeCode(KakaoAuthorizeCodeRequestDto requestDto) {
        return ResponseWrapper.ok(kakaoService.getAuthorizeCode(requestDto));
    }*/

    @GetMapping ("/login/oauth2/code/{loginType}")
    public ResponseWrapper getKakaoOauthAuthorizationCode(@PathVariable SnsLoginType loginType,
                                                          Oauth2LoginRequestDto requestDto) {
        return ResponseWrapper.ok(oauth2Service.login(loginType, requestDto));
    }


    /*@PostMapping("/oauth/token")
    public ResponseWrapper getKakaoOauthToken(@RequestBody KakaoOauthTokenDto requestDto) {
        System.out.println("asdasd");
        return null;
    }*/


}