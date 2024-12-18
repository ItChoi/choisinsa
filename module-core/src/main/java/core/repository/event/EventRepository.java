package core.repository.event;

import core.domain.event.Event;
import core.common.enumeration.ActiveStatus;
import core.common.enumeration.event.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByEventTypeAndEventStatus(EventType inviteFriend,
                                                 ActiveStatus active);
}
