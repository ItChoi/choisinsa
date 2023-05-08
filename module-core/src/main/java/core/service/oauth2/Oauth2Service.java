package core.service.oauth2;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.SnsLoginType;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import core.dto.request.oauth2.Oauth2LoginRequestDto;
import core.dto.response.oauth2.Oauth2LoginResponseDto;
import core.service.oauth2.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class Oauth2Service {

    private final KakaoService kakaoService;

    public Oauth2LoginResponseDto login(SnsLoginType loginType,
                                        Oauth2LoginRequestDto requestDto) {

        if (loginType == SnsLoginType.KAKAO) {
            return kakaoService.authorizeOauth(requestDto);
        }

        throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
    }
}
