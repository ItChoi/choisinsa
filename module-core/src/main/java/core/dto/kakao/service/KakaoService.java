package core.dto.kakao.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import core.dto.kakao.dto.KakaoOauthAuthorizationCodeDto;
import core.dto.kakao.dto.KakaoOauthTokenDto;
import core.dto.kakao.dto.request.KakaoAuthorizeCodeRequestDto;
import core.dto.kakao.dto.request.KakaoOauthTokenRequestDto;
import core.service.authority.MemberCertificationService;
import core.service.http.HttpCommunication;
import io.micrometer.core.lang.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static com.mall.choisinsa.common.secret.ConstData.*;

@RequiredArgsConstructor
@Service
public class KakaoService {

    private final HttpCommunication httpCommunication;
    private final MemberCertificationService memberCertificationService;

    @Value("${oauth.service.kakao.client_id}")
    private String clientId;
    @Value("${oauth.service.kakao.redirect_uri}")
    private String redirectUri;
    @Value("${oauth.service.kakao.client_secret}")
    private String clientSecret;

    public Object authorizeOauth(KakaoOauthAuthorizationCodeDto requestDto) {
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
        KakaoOauthTokenDto test = objectMapper.convertValue(oauthCode, KakaoOauthTokenDto.class);
        System.out.println("test");

        return null;
    }

    @Nullable
    public KakaoOauthAuthorizationCodeDto getAuthorizeCode(KakaoAuthorizeCodeRequestDto requestDto) {
        Object obj = httpCommunication.requestGet(KAKAO_OAUTH_AUTHORIZE_URL, requestDto);

        ObjectMapper objectMapper = new ObjectMapper();
        if (obj == null) {
            return null;
        }

        try {
            String jsonString = objectMapper.writeValueAsString(obj);
            return objectMapper.readValue(jsonString, KakaoOauthAuthorizationCodeDto.class);
        } catch (JsonProcessingException e) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }
    }
}
