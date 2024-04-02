package core.dto.client.response.oauth2;

import com.mall.choisinsa.enumeration.SnsType;
import core.dto.general.JwtTokenDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Oauth2LoginResponseDto {
    private JwtTokenDto jwtTokenDto;
    private String oauth2AccessToken;
    private SnsType snsType;
    private Oauth2ResponseDto oauth2UserInfo;
}
