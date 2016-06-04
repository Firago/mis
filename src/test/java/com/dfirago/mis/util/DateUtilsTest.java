package com.dfirago.mis.util;

import org.junit.Test;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by dmfi on 04/06/2016.
 */
public class DateUtilsTest {

    @Test
    public void testGetWeekStart() {
        ZonedDateTime date = ZonedDateTime.of(2016, 6, 4, 12, 18, 55, 0, ZoneId.systemDefault());
        ZonedDateTime weekStart = DateUtils.getWeekStart(date);
        assertTrue(weekStart.isBefore(date));
        assertEquals(DayOfWeek.MONDAY, weekStart.getDayOfWeek());
        assertEquals(2016, weekStart.getYear());
        assertEquals(Month.MAY, weekStart.getMonth());
        assertEquals(30, weekStart.getDayOfMonth());
        assertEquals(0, weekStart.getHour());
        assertEquals(0, weekStart.getMinute());
        assertEquals(0, weekStart.getSecond());
    }

    @Test
    public void testGetWeekEnd() {
        ZonedDateTime date = ZonedDateTime.of(2016, 6, 4, 12, 18, 55, 0, ZoneId.systemDefault());
        ZonedDateTime weekEnd = DateUtils.getWeekEnd(date);
        assertTrue(weekEnd.isAfter(date));
        assertEquals(DayOfWeek.SUNDAY, weekEnd.getDayOfWeek());
        assertEquals(2016, weekEnd.getYear());
        assertEquals(Month.JUNE, weekEnd.getMonth());
        assertEquals(5, weekEnd.getDayOfMonth());
        assertEquals(23, weekEnd.getHour());
        assertEquals(59, weekEnd.getMinute());
        assertEquals(59, weekEnd.getSecond());
    }

}
