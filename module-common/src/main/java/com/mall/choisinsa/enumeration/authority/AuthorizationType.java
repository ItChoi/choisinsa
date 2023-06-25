package com.mall.choisinsa.enumeration.authority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

@Getter
@RequiredArgsConstructor
public enum AuthorizationType {
    AUTHORIZATION("Authorization");

    private final String type;
}
