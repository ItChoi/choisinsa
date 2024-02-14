package core.repository.redis;

import core.domain.redis.RedisHashToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RedisHashTokenRepository extends CrudRepository<RedisHashToken, String> {

    Optional<RedisHashToken> findByToken(String token);

    Optional<RedisHashToken> findByAuthId(String authId);

}
