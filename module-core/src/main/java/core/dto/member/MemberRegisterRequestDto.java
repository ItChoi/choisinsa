package core.dto.member;

import com.mall.choisinsa.enumeration.member.LoginType;
import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberRegisterRequestDto {

    @NotBlank
    private String loginId;
    private String name;
    @NotBlank
    private String email;
    private String recommenderLoginId;
    private String nickName;
    private String phoneNumber;
    private String profileFileUrl;
    private LoginType loginType;
}


