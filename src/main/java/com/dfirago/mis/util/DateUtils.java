package com.dfirago.mis.util;

import java.text.DateFormatSymbols;
import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.util.Locale;

/**
 * Created by dmfi on 15/05/2016.
 */
public class DateUtils {

    public static int timeToMinutes(ZonedDateTime time) {
        int hours = time.getHour();
        int minutes = time.getMinute();
        return (hours * 60) + minutes;
    }

    public static int timeToMinutes(ZonedDateTime time, int offset) {
        return timeToMinutes(time.plusMinutes(offset));
    }

    public static String getWeekName(int weekIndex, String locale) {
        DateFormatSymbols symbols = new DateFormatSymbols(new Locale(locale));
        String[] dayNames = symbols.getWeekdays();
        if (weekIndex == 7) {
            return dayNames[1];
        } else {
            return dayNames[weekIndex + 1];
        }
    }

    public static ZonedDateTime getWeekStart(ZonedDateTime date) {
        ZonedDateTime dayStart = date
            .withHour(0)
            .withMinute(0)
            .withSecond(0)
            .withNano(0);
        DayOfWeek dayOfWeek = dayStart.getDayOfWeek();
        return dayStart.minusDays(dayOfWeek.getValue() - 1);
    }

    public static ZonedDateTime getWeekEnd(ZonedDateTime date) {
        ZonedDateTime dayEnd = date
            .withHour(23)
            .withMinute(59)
            .withSecond(59)
            .withNano(999999999);
        DayOfWeek dayOfWeek = dayEnd.getDayOfWeek();
        return dayEnd.plusDays(7 - dayOfWeek.getValue());
    }
}
