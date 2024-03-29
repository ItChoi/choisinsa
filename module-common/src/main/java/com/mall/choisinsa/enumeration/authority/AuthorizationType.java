package com.mall.choisinsa.enumeration.authority;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthorizationType {
    AUTHORIZATION("Authorization");

    private final String type;
}
