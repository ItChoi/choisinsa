package core.service.event;

import core.domain.event.Event;
import core.repository.event.EventRepository;
import com.mall.choisinsa.enumeration.ActiveStatus;
import com.mall.choisinsa.enumeration.event.EventType;
import core.repository.event.EventParticipantRepository;
import com.mall.choisinsa.util.DateUtil;
import io.micrometer.core.lang.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventParticipantRepository eventParticipantRepository;

    @Transactional(readOnly = true)
    public boolean canRecommendByMemberId(Long memberId) {
        Event event = getOneWithEventOn(EventType.INVITE_FRIEND);
        if (event == null) return false;

        return eventParticipantRepository.existsByMemberIdAndEventId(memberId, event.getId());
    }

    @Nullable
    private Event getOneWithEventOn(EventType eventType) {
        List<Event> activeEvents = eventRepository.findAllByEventTypeAndEventStatus(eventType, ActiveStatus.ACTIVE);
        return activeEvents.stream()
                .filter(this::isEventOn)
                .findFirst()
                .orElse(null);
    }

    private boolean isEventOn(Event event) {
        return DateUtil.isIncludePeriodWithNow(event.getEventStartDt(), event.getEventEndDt());
    }
}
