package core.service.oauth2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import core.dto.request.oauth2.Oauth2LoginRequestDto;
import core.dto.general.KakaoOauthTokenDto;
import core.dto.request.kakao.KakaoAuthorizeCodeRequestDto;
import core.dto.request.kakao.KakaoOauthTokenRequestDto;
import core.dto.response.oauth2.Oauth2LoginResponseDto;
import core.service.authority.MemberCertificationService;
import core.http.HttpCommunication;
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
        // TODO 이메일 가져와서 체크 필요
        return new Oauth2LoginResponseDto(oauth2TokenInfo.getAccess_token(), false);
    }

    @Nullable
    public Oauth2LoginRequestDto getAuthorizeCode(KakaoAuthorizeCodeRequestDto requestDto) {
        Object obj = httpCommunication.requestGet(KAKAO_OAUTH_AUTHORIZE_URL, requestDto);

        ObjectMapper objectMapper = new ObjectMapper();
        if (obj == null) {
            return null;
        }

        try {
            String jsonString = objectMapper.writeValueAsString(obj);
            return objectMapper.readValue(jsonString, Oauth2LoginRequestDto.class);
        } catch (JsonProcessingException e) {
            throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
        }
    }
}
