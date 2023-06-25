package core.dto.response.oauth2;

import com.mall.choisinsa.enumeration.member.GenderType;
import com.mall.choisinsa.enumeration.member.LoginType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
public class Oauth2UserResponseDto {
    @Setter
    private boolean isAlreadyEmail; // 이메일만 존재, 연동 여부 이용자에게 체크 필요
    @Setter
    private boolean isExistsSnsLoginId; // 로그인 성공
    // OAuth 고유 id
    private String id;
    private String nickname;
    private String profileImageUrl;
    // 이용자 이름
    private String name;
    private String email;
    private GenderType gender;
    private String birthyear;
    private String birthday;
    private String phoneNumber;
    private LoginType loginType;

    public Oauth2UserResponseDto(String id,
                                 String nickname,
                                 String profileImageUrl,
                                 String email,
                                 String gender,
                                 LoginType loginType) {
        this.id = id;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.email = email;
        this.gender = GenderType.from(gender);
        this.loginType = loginType;
    }

    public String getSnsLoginId() {
        if (this.loginType == null || !StringUtils.hasText(this.id)) {
            return "";
        }

        return this.loginType + this.id;
    }
}
