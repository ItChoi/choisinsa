package core.domain.redis;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@RedisHash(value = "redis_hash_token")
public class RedisHashToken {
    /**
     * @Id, @Indexed 적용 필드만 CrudRepository findBy~ 구문 사용 가능
     */
    @Id
    private String authId;

    @Indexed
    private String token;

    //private String role;

    @TimeToLive
    private long expirationSec;

    public RedisHashToken update(String token,
                                 long expirationSec) {
        this.token = token;
        this.expirationSec = expirationSec;
        return this;
    }
}
