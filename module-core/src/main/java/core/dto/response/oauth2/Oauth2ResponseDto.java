package core.dto.response.oauth2;

import com.mall.choisinsa.enumeration.SnsType;
import core.dto.response.member.MemberSnsConnectInfoResponseDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
public class Oauth2ResponseDto {
    private MemberSnsConnectInfoResponseDto snsConnectInfo;
    private Oauth2UserResponseDto memberInfo;

    public Oauth2ResponseDto(MemberSnsConnectInfoResponseDto snsConnectInfo,
                             Oauth2UserResponseDto memberInfo) {
        this.snsConnectInfo = snsConnectInfo;
        this.memberInfo = memberInfo;
    }
}
