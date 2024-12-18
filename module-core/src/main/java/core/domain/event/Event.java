package core.domain.event;

import core.domain.common.BaseDateTimeEntity;
import core.common.enumeration.ActiveStatus;
import core.common.enumeration.EntryFeeType;
import core.common.enumeration.event.EventMethod;
import core.common.enumeration.event.EventType;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Entity
public class Event extends BaseDateTimeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @OneToMany(mappedBy = "event", orphanRemoval = true, cascade = CascadeType.ALL)
    private Set<EventParticipant> eventParticipants;
    @Column
    private Long categoryId;
    @Enumerated(EnumType.STRING)
    @Column
    private EventMethod eventMethod;
    @Enumerated(EnumType.STRING)
    @Column
    private EventType eventType;
    @Column
    private String title;
    @Column
    private String content;
    @Column
    private LocalDateTime eventStartDt;
    @Column
    private LocalDateTime eventEndDt;
    @Enumerated(EnumType.STRING)
    @Column
    private ActiveStatus eventStatus;
    @Enumerated(EnumType.STRING)
    @Column
    private EntryFeeType entryFeeType;
    @Column
    private int participationLimitCount;
}