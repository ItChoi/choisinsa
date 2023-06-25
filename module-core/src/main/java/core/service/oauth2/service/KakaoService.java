package core.service.oauth2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.authority.AuthorizationType;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import com.mall.choisinsa.enumeration.member.LoginType;
import core.domain.member.Member;
import core.dto.request.oauth2.Oauth2LoginRequestDto;
import core.dto.general.KakaoOauthTokenDto;
import core.dto.request.kakao.KakaoAuthorizeCodeRequestDto;
import core.dto.request.kakao.KakaoOauthTokenRequestDto;
import core.dto.response.oauth2.Oauth2LoginResponseDto;
import core.dto.response.oauth2.Oauth2UserResponseDto;
import core.service.authority.MemberCertificationService;
import core.http.HttpCommunication;
import core.service.member.MemberService;
import io.micrometer.core.lang.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

import static com.mall.choisinsa.common.secret.ConstData.*;

@RequiredArgsConstructor
@Service
public class KakaoService {

    private final HttpCommunication httpCommunication;
    private final MemberCertificationService memberCertificationService;
    private final MemberService memberService;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;
    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;

    public Oauth2LoginResponseDto authorizeOauth(Oauth2LoginRequestDto requestDto) {
        String code = requestDto.getCode();
        if (!StringUtils.hasText(code)) {
            throw new ErrorTypeAdviceException(ErrorType.NOT_EXISTS_REQUIRED_DATA);
        }

        KakaoOauthTokenRequestDto authorizationCode = KakaoOauthTokenRequestDto.builder()
                .grant_type(KAKAO_GRANT_TYPE)
                .client_id(clientId)
                .redirect_uri(redirectUri)
                .code(code)
                .client_secret(clientSecret)
                .build();

        Object oauthCode = httpCommunication.requestPost(
                KAKAO_OAUTH_TOKEN_URL,
                MediaType.APPLICATION_FORM_URLENCODED,
                authorizationCode
        );

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoOauthTokenDto oauth2TokenInfo = objectMapper.convertValue(oauthCode, KakaoOauthTokenDto.class);
        return new Oauth2LoginResponseDto(oauth2TokenInfo.getAccess_token(), false);
    }

    @Nullable
    public Oauth2LoginRequestDto getAuthorizeCode(KakaoAuthorizeCodeRequestDto requestDto) {
        Object obj = httpCommunication.requestGet(KAKAO_OAUTH_AUTHORIZE_URL, requestDto);

        if (obj == null) {
            return null;
        }

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(obj);
            return objectMapper.readValue(jsonString, Oauth2LoginRequestDto.class);
        } catch (JsonProcessingException e) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }
    }

    public Oauth2UserResponseDto getUser(String oauth2AcessToken) {
        Object obj = httpCommunication.requestPostWithToken(KAKAO_GET_USER_URL, MediaType.APPLICATION_FORM_URLENCODED, AuthorizationType.AUTHORIZATION, oauth2AcessToken);
        Oauth2UserResponseDto responseDto = getUserWithKakaoInfo(obj);

        responseDto.setExistsSnsLoginId(memberService.isExistLoginId(responseDto.getSnsLoginId()));
        if (responseDto.isExistsSnsLoginId()) {
            return responseDto;
        }

        responseDto.setAlreadyEmail(memberService.isExistEmail(responseDto.getEmail()));
        return responseDto;
    }


    private Oauth2UserResponseDto getUserWithKakaoInfo(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonString = objectMapper.writeValueAsString(obj);
            Map<String, Object> getKakaoUserWithProperties = objectMapper.readValue(jsonString, HashMap.class);

            Map<String, Object> kakaoAccount = (Map<String, Object>) getKakaoUserWithProperties.get("kakao_account");
            String id = ObjectUtils.nullSafeToString(getKakaoUserWithProperties.get("id"));
            String nickname = ObjectUtils.nullSafeToString(((Map<String, Object>) kakaoAccount.get("profile")).get("nickname"));
            String profile_image_url = ObjectUtils.nullSafeToString(((Map<String, Object>) kakaoAccount.get("profile")).get("profile_image_url"));
            String email = ObjectUtils.nullSafeToString(kakaoAccount.get("email"));
            String gender = ObjectUtils.nullSafeToString(kakaoAccount.get("gender"));

            return new Oauth2UserResponseDto(id, nickname, profile_image_url, email, gender, LoginType.KAKAO);
        } catch (JsonProcessingException e) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }
    }
}
