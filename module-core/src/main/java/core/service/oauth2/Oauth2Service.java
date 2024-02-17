package core.service.oauth2;

import com.mall.choisinsa.common.exception.ErrorTypeAdviceException;
import com.mall.choisinsa.enumeration.SnsType;
import com.mall.choisinsa.enumeration.exception.ErrorType;
import com.mall.choisinsa.security.dto.JwtTokenDto;
import com.mall.choisinsa.security.service.SecurityMemberService;
import core.dto.client.request.oauth2.Oauth2LoginRequestDto;
import core.dto.client.response.oauth2.Oauth2LoginResponseDto;
import core.dto.client.response.oauth2.Oauth2ResponseDto;
import core.dto.client.response.oauth2.Oauth2UserResponseDto;
import core.dto.general.CoreJwtTokenDto;
import core.service.oauth2.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("client")
@RequiredArgsConstructor
@Service
public class Oauth2Service {

    private final KakaoService kakaoService;
    private final SecurityMemberService securityMemberService;

    public Oauth2LoginResponseDto login(SnsType snsType,
                                        Oauth2LoginRequestDto requestDto) {

        if (snsType == SnsType.KAKAO) {
            String oauth2Token = kakaoService.authorizeOauth(requestDto);
            Oauth2ResponseDto oauth2UserInfo = kakaoService.getUser(oauth2Token);

            JwtTokenDto jwtTokenDto = null;
            if (oauth2UserInfo.getIsAlreadyConnectSns()) {
                Oauth2UserResponseDto memberInfo = oauth2UserInfo.getMemberInfo();
                if (memberInfo != null) {
                    jwtTokenDto = securityMemberService.loginWithSns(snsType, memberInfo.getId());
                }
            }
            return new Oauth2LoginResponseDto(
                    new CoreJwtTokenDto(jwtTokenDto),
                    oauth2Token,
                    snsType,
                    oauth2UserInfo
            );
        }

        throw new ErrorTypeAdviceException(ErrorType.BAD_REQUEST);
    }
}
