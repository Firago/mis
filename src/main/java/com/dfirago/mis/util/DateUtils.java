package com.dfirago.mis.util;

import java.text.DateFormatSymbols;
import java.time.ZonedDateTime;
import java.util.Locale;

/**
 * Created by dmfi on 15/05/2016.
 */
public class DateUtils {

    public static int timeToMinutes(ZonedDateTime time) {
        int hours = time.getHour() + 1;
        int minutes = time.getMinute() + 1;
        return (hours * 60) + minutes;
    }

    public static int timeToMinutes(ZonedDateTime time, int offset) {
        return timeToMinutes(time.minusMinutes(offset));
    }

    public static String getWeekName(int weekIndex, String locale) {
        DateFormatSymbols symbols = new DateFormatSymbols(new Locale(locale));
        String[] dayNames = symbols.getWeekdays();
        return dayNames[weekIndex];
    }
}
