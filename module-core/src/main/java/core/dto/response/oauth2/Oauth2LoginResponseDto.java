package core.dto.response.oauth2;

import com.mall.choisinsa.enumeration.SnsType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Oauth2LoginResponseDto {
    private SnsType loginType;
    private String jwtAccessToken;
    private String oauth2AcessToken;
    private Oauth2ResponseDto oauth2UserInfo;
}
