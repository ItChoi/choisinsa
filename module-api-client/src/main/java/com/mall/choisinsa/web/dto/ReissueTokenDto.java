package com.mall.choisinsa.web.dto;

import core.dto.general.CoreReissueTokenDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReissueTokenDto {

    @NotBlank
    private String expiredAccessToken;

    public CoreReissueTokenDto convert() {
        return new CoreReissueTokenDto(expiredAccessToken);
    }
}
