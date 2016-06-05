package com.dfirago.mis.domain.util;

import com.dfirago.mis.domain.Lesson;
import com.dfirago.mis.domain.User;
import com.dfirago.mis.util.DateUtils;
import com.dfirago.mis.util.StringUtils;
import com.dfirago.mis.web.rest.dto.DayDTO;
import com.dfirago.mis.web.rest.dto.LessonDTO;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by dmfi on 15/05/2016.
 */
public class TimetableUtils {

    public static List<LessonDTO> convertLessonToDtoWithTeacher(List<Lesson> lessons) {
        List<LessonDTO> result = lessons.stream()
            .map(TimetableUtils::convertLessonToDtoWithTeacher)
            .collect(Collectors.toList());
        return result;
    }

    public static LessonDTO convertLessonToDtoWithTeacher(Lesson lesson) {
        LessonDTO dto = convertLessonToDtoCommon(lesson);
        User user = lesson.getTeacher().getUser();
        StringBuilder sb = new StringBuilder()
            .append(user.getFirstName())
            .append(" ")
            .append(user.getLastName());
        dto.setWith(sb.toString());
        return dto;
    }

    public static List<LessonDTO> convertLessonToDtoWithGroup(List<Lesson> lessons) {
        List<LessonDTO> result = lessons.stream()
            .map(TimetableUtils::convertLessonToDtoWithGroup)
            .collect(Collectors.toList());
        return result;
    }

    public static LessonDTO convertLessonToDtoWithGroup(Lesson lesson) {
        LessonDTO dto = convertLessonToDtoCommon(lesson);
        dto.setWith(lesson.getSubjectEntry().getStudentGroup().getName());
        return dto;
    }

    private static LessonDTO convertLessonToDtoCommon(Lesson lesson) {
        LessonDTO dto = new LessonDTO();
        dto.setTitle(lesson.getSubjectEntry().getSubject().getName());
        dto.setStart(DateUtils.timeToMinutes(lesson.getStart()));
        dto.setEnd(DateUtils.timeToMinutes(lesson.getStart(), lesson.getDuration()));
        dto.setRoom(lesson.getRoom());
        dto.setType(lesson.getSubjectEntry().getSubject().getType().getShortName());
        return dto;
    }

    public static List<DayDTO> generateEmptyDays(final ZonedDateTime from, final ZonedDateTime to) {
        List<DayDTO> result = new ArrayList<>();
        ZonedDateTime iterator = from;
        while (iterator.isBefore(to)) {
            result.add(zonedDateTimeToDayDto(iterator));
            iterator = iterator.plusDays(1);
        }
        return result;
    }

    public static List<DayDTO> groupLessonsByDaysForGroup(final List<Lesson> lessons, final ZonedDateTime from, final ZonedDateTime to) {
        List<DayDTO> days = TimetableUtils.generateEmptyDays(from, to);
        for (DayDTO day : days) {
            lessons.stream().filter(
                lesson -> zonedDateTimeToDayDto(lesson.getStart()).equals(day))
                .forEach(
                    lesson1 -> day.getLessons().add(convertLessonToDtoWithTeacher(lesson1)));
        }
        return days;
    }

    public static List<DayDTO> groupLessonsByDaysForTeacher(final List<Lesson> lessons, final ZonedDateTime from, final ZonedDateTime to) {
        List<DayDTO> days = TimetableUtils.generateEmptyDays(from, to);
        for (DayDTO day : days) {
            lessons.stream().filter(
                lesson -> zonedDateTimeToDayDto(lesson.getStart()).equals(day))
                .forEach(
                    lesson1 -> day.getLessons().add(convertLessonToDtoWithGroup(lesson1)));
        }
        return days;
    }

    private static DayDTO zonedDateTimeToDayDto(ZonedDateTime zonedDateTime) {
        String title = DateUtils.getWeekName(zonedDateTime.getDayOfWeek().getValue(), "pl");
        int year = zonedDateTime.getYear();
        int month = zonedDateTime.getMonthValue();
        int day = zonedDateTime.getDayOfMonth();
        ZoneId zoneId = zonedDateTime.getZone();
        ZonedDateTime ddmmyyyy = ZonedDateTime.of(year, month, day, 0, 0, 0, 0, zoneId);
        DayDTO dayDTO = new DayDTO();
        dayDTO.setTitle(StringUtils.capitalize(title));
        dayDTO.setDate(ddmmyyyy);
        return dayDTO;
    }
}
