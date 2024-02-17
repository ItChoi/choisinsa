package com.mall.choisinsa.web.dto;

import core.dto.general.CoreJwtTokenDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtTokenDto {
    private String accessToken;
    private String refreshToken;

    public JwtTokenDto(CoreJwtTokenDto coreJwtTokenDto) {
        this.accessToken = coreJwtTokenDto.getAccessToken();
        this.refreshToken = coreJwtTokenDto.getRefreshToken();
    }
}
