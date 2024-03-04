package com.mall.choisinsa.web.dto;

import core.dto.general.CoreJwtTokenDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtTokenDto {

    @NotBlank
    private String accessToken;

    @NotBlank
    private String refreshToken;

    public JwtTokenDto(CoreJwtTokenDto coreJwtTokenDto) {
        this.accessToken = coreJwtTokenDto.getAccessToken();
        this.refreshToken = coreJwtTokenDto.getRefreshToken();
    }

    public CoreJwtTokenDto convert() {
        return new CoreJwtTokenDto(this.accessToken, this.refreshToken);
    }
}
