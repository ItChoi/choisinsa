package core.dto.response.oauth2;

import com.mall.choisinsa.enumeration.SnsType;
import com.mall.choisinsa.enumeration.member.GenderType;
import lombok.Getter;

@Getter
public class Oauth2UserResponseDto {
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
    private SnsType snsType;

    public Oauth2UserResponseDto(String id,
                                 String nickname,
                                 String profileImageUrl,
                                 String email,
                                 String gender,
                                 SnsType snsType) {
        this.id = id;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
        this.email = email;
        this.gender = GenderType.from(gender);
        this.snsType = snsType;
    }
}
