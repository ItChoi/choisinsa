package core.domain.event;

import com.mall.choisinsa.domain.BaseDateTimeEntity;
import com.mall.choisinsa.enumeration.DayOfWeekType;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class EventSpecificPointInTime extends BaseDateTimeEntity {

    @Id
    private Long eventId;
    @Enumerated(EnumType.STRING)
    @Column
    private DayOfWeekType eventDayOfWeekType;
    @Column
    private int eventHour;
    @Column
    private int eventMinute;
    @Column
    private String locale;
}