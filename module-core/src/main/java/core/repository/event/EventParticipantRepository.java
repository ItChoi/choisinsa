package core.repository.event;

import core.domain.event.EventParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventParticipantRepository extends JpaRepository<EventParticipant, Long> {
    boolean existsByMemberIdAndEventId(Long memberId, Long eventId);
}
