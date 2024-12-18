package core.service.member;

import core.common.redis.RedisKeyGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import redis.service.RedisService;

@RequiredArgsConstructor
@Service
public class CoreMemberService {

    private final RedisService redisService;

    @Value("${jwt.refresh-token.validity-in-seconds}")
    private long refreshTokenValidityInMilliseconds;

    public void setInMemory(String loginId,
                            String refreshToken) {
        redisService.setData(
                RedisKeyGenerator.jwtRefreshToken(loginId),
                refreshToken,
                refreshTokenValidityInMilliseconds / 1000
        );
    }

    public String getInMemory(String key) {
        return redisService.getData(RedisKeyGenerator.jwtRefreshToken(key));
    }
}
