package core.service.oauth2;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.SnsType;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import com.mall.choisinsa.security.provider.JwtTokenProvider;
import core.dto.request.oauth2.Oauth2LoginRequestDto;
import core.dto.response.oauth2.Oauth2LoginResponseDto;
import core.dto.response.oauth2.Oauth2ResponseDto;
import core.service.oauth2.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class Oauth2Service {

    private final KakaoService kakaoService;
    private final JwtTokenProvider jwtTokenProvider;

    public Oauth2LoginResponseDto login(SnsType loginType,
                                        Oauth2LoginRequestDto requestDto) {

        if (loginType == SnsType.KAKAO) {
            String oauth2Token = kakaoService.authorizeOauth(requestDto);
            Oauth2ResponseDto oauth2UserInfo = kakaoService.getUser(oauth2Token);

            String jwtAccessToken = null;
            if (oauth2UserInfo.getHasSnsLogin()) {
                jwtAccessToken = jwtTokenProvider.createTokenWithSnsLogin(loginType, oauth2Token);
            }
            return new Oauth2LoginResponseDto(
                    loginType,
                    jwtAccessToken,
                    oauth2Token,
                    oauth2UserInfo
            );

        }

        throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
    }
}
