package core.dto.client.request.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class SnsMemberRegisterRequestDto {
    private String snsId;
    private String oauth2AccessToken;
    private MemberRegisterRequestDto memberInfo;
}
