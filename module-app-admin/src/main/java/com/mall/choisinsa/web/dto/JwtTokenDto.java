package com.mall.choisinsa.web.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtTokenDto {
    private String accessToken;
    private String refreshToken;

    public JwtTokenDto(core.dto.general.JwtTokenDto jwtTokenDto) {
        this.accessToken = jwtTokenDto.getAccessToken();
        this.refreshToken = jwtTokenDto.getRefreshToken();
    }
}
