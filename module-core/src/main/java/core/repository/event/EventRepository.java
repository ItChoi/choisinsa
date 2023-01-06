package core.repository.event;

import core.domain.event.Event;
import com.mall.choisinsa.enumeration.ActiveStatus;
import com.mall.choisinsa.enumeration.event.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByEventTypeAndEventStatus(EventType inviteFriend,
                                                 ActiveStatus active);
}
