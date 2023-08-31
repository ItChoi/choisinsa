package core.repository.hashtag;

import core.domain.hashtag.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {

    List<HashTag> findByIdIn(Collection<Long> hashTagIds);
}