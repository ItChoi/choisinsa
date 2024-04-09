package com.mall.choisinsa.util.date;

import java.time.LocalDateTime;

public class DateUtil {

    public static boolean isIncludePeriodWithNow(LocalDateTime startDt,
                                                 LocalDateTime endDt) {
        LocalDateTime now = LocalDateTime.now();

        return now.minusMinutes(1).isBefore(endDt) && now.plusMinutes(1).isAfter(startDt);
    }
}
