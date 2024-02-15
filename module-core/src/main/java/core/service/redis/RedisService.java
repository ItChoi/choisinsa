package core.service.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 메서드명	    레디스 타입
     * opsForValue	String
     * opsForList	List
     * opsForSet	Set
     * opsForZSet	Sorted Set
     * opsForHash	Hash
     */

    public void setData(String key,
                        String value,
                        Long expiredSec) {
        redisTemplate.opsForValue()
                .set(key, value, expiredSec, TimeUnit.SECONDS);
    }

    public String getData(String key) {
        return redisTemplate.opsForValue().get(key).toString();
    }

    public void deleteData(String key) {
        redisTemplate.delete(key);
    }
}
