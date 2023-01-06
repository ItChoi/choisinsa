package core.repository;

import core.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LockRepository extends JpaRepository<Stock, Long> {
    // TODO: 원래는 별도의 JDBC를 사용하여 LOCK 코드를 작성해야 한다.

    @Query(value = "SELECT get_lock(:key, 3000)", nativeQuery = true)
    void getLock(@Param("key") String key);

    @Query(value = "SELECT release_lock(:key)", nativeQuery = true)
    void releaseLock(@Param("key") String key);

}
