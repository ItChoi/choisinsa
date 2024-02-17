package core.dto.general;

import com.mall.choisinsa.security.dto.JwtTokenDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CoreJwtTokenDto {
    private String accessToken;
    private String refreshToken;

    public CoreJwtTokenDto(JwtTokenDto jwtTokenDto) {
        this.accessToken = jwtTokenDto.getAccessToken();
        this.refreshToken = jwtTokenDto.getRefreshToken();
    }
}
