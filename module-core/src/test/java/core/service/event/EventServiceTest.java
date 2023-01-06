package core.service.event;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class EventServiceTest {

    @Test
    void isEventOn() {
        LocalDateTime now1 = LocalDateTime.of(2022, 4, 25, 17, 30, 00);
        LocalDateTime now2 = LocalDateTime.of(2022, 4, 25, 17, 33, 00);
        LocalDateTime now3 = LocalDateTime.of(2022, 4, 25, 17, 38, 00);
        LocalDateTime now4 = LocalDateTime.of(2022, 4, 25, 17, 40, 00);
        LocalDateTime now5 = LocalDateTime.of(2022, 4, 25, 17, 29, 00);
        LocalDateTime now6 = LocalDateTime.of(2022, 4, 25, 17, 41, 00);

        LocalDateTime startDt = LocalDateTime.of(2022, 4, 25, 17, 30, 00);
        LocalDateTime endDt = LocalDateTime.of(2022, 4, 25, 17, 40, 00);

        Assertions.assertThat(now1.minusMinutes(1).isBefore(endDt) && now1.plusMinutes(1).isAfter(startDt)).isTrue();
        Assertions.assertThat(now2.minusMinutes(1).isBefore(endDt) && now2.plusMinutes(1).isAfter(startDt)).isTrue();
        Assertions.assertThat(now3.minusMinutes(1).isBefore(endDt) && now3.plusMinutes(1).isAfter(startDt)).isTrue();
        Assertions.assertThat(now4.minusMinutes(1).isBefore(endDt) && now4.plusMinutes(1).isAfter(startDt)).isTrue();
        Assertions.assertThat(now5.minusMinutes(1).isBefore(endDt) && now5.plusMinutes(1).isAfter(startDt)).isFalse();
        Assertions.assertThat(now6.minusMinutes(1).isBefore(endDt) && now6.plusMinutes(1).isAfter(startDt)).isFalse();
    }

}