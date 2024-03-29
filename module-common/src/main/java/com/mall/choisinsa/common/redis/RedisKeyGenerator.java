package com.mall.choisinsa.common.redis;

public class RedisKeyGenerator {

    private static final String JWT_REFRESH_TOKEN_KEY_SUFFIX = ":refreshToken";

    public static String jwtRefreshToken(String loginId) {
        return loginId + JWT_REFRESH_TOKEN_KEY_SUFFIX;
    }
}
