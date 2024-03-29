package core.dto.general;

import core.dto.JwtTokenDto;
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

    public JwtTokenDto convert() {
        return new JwtTokenDto(this.accessToken, this.refreshToken);
    }
}
