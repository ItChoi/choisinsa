package core.service.hashtag;

import core.domain.hashtag.HashTag;
import core.repository.hashtag.HashTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
@Service
public class HashTagService {

    private final HashTagRepository hashTagRepository;

    @Transactional(readOnly = true)
    public List<HashTag> findByIdIn(Collection<Long> hashTagIds) {
        return hashTagRepository.findByIdIn(hashTagIds);
    }
}
