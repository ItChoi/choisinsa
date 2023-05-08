package core.dto.response.oauth2;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Oauth2LoginResponseDto {
    private String oauth2AcessToken;
    private Boolean isAlreadyMember;
}
