package core.service.oauth2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.SnsType;
import com.mall.choisinsa.enumeration.authority.AuthorizationType;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import core.domain.member.Member;
import core.domain.member.MemberSnsConnect;
import core.dto.request.oauth2.Oauth2LoginRequestDto;
import core.dto.general.KakaoOauthTokenDto;
import core.dto.request.kakao.KakaoAuthorizeCodeRequestDto;
import core.dto.request.kakao.KakaoOauthTokenRequestDto;
import core.dto.response.member.MemberSnsConnectInfoResponseDto;
import core.dto.response.oauth2.Oauth2ResponseDto;
import core.dto.response.oauth2.Oauth2UserResponseDto;
import core.service.authority.MemberCertificationService;
import core.http.HttpCommunication;
import core.service.member.MemberService;
import io.micrometer.core.lang.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public String authorizeOauth(Oauth2LoginRequestDto requestDto) {
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
        return oauth2TokenInfo.getAccess_token();
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

    public Oauth2ResponseDto getUser(String oauth2AcessToken) {
        Object obj = httpCommunication.requestPostWithToken(
                KAKAO_GET_USER_URL,
                MediaType.APPLICATION_FORM_URLENCODED,
                AuthorizationType.AUTHORIZATION,
                oauth2AcessToken);

        Oauth2UserResponseDto snsUserInfo = getUserWithKakaoInfo(obj);
        return new Oauth2ResponseDto(
                getSnsConnectInfo(snsUserInfo.getId(), snsUserInfo.getSnsType(), snsUserInfo.getEmail()),
                snsUserInfo
        );
    }

    @Transactional(readOnly = true)
    public MemberSnsConnectInfoResponseDto getSnsConnectInfo(String snsId,
                                                             SnsType snsType,
                                                             String email) {
        MemberSnsConnect memberSnsConnect = memberService.findSnsMemberBySnsIdAndSnsType(snsId, snsType);
        if (memberSnsConnect == null) {
            return new MemberSnsConnectInfoResponseDto(null, null);
        }

        boolean isAlreadyExistsEmail;
        Member member = memberService.findByIdOrThrow(memberSnsConnect.getMemberId());
        if (StringUtils.hasText(email) && email.equals(member.getEmail())) {
            isAlreadyExistsEmail = memberService.isExistEmail(email);
        }

        responseDto.setHasSnsLogin(memberService.isExistLoginId(responseDto.getSnsLoginId()));
        responseDto.setIsAlreadyExistEmail(memberService.isExistEmail(responseDto.getEmail()));

        return new MemberSnsConnectInfoResponseDto(null, null);
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

            return new Oauth2UserResponseDto(id, nickname, profile_image_url, email, gender, SnsType.KAKAO);
        } catch (JsonProcessingException e) {
            throw new ErrorTypeAdviceException(ErrorType.CAN_NOT_JSON_CONVERT);
        }
    }
}
