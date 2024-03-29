package com.mall.choisinsa.security.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReissueTokenDto {

    private String expiredAccessToken;

    public ReissueTokenDto convert() {
        return new ReissueTokenDto(expiredAccessToken);
    }
}
