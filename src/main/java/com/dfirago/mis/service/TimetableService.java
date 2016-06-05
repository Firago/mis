package com.dfirago.mis.service;

import com.dfirago.mis.domain.Lesson;
import com.dfirago.mis.domain.util.TimetableUtils;
import com.dfirago.mis.repository.LessonRepository;
import com.dfirago.mis.web.rest.dto.DayDTO;
import com.dfirago.mis.web.rest.dto.TimetableResponseDTO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by dmfi on 15/05/2016.
 */
@Service
public class TimetableService {

    @Inject
    private LessonRepository lessonRepository;

    public TimetableResponseDTO generateTimetableForGroup(Long groupId, ZonedDateTime from, ZonedDateTime to) {
        List<Lesson> lessons = lessonRepository.findAllByGroupIdAndStartBetweenFromAndTo(groupId, from, to);
        TimetableResponseDTO timetable = new TimetableResponseDTO();
        List<DayDTO> days = TimetableUtils.groupLessonsByDaysForGroup(lessons, from, to);
        timetable.setDays(days);
        timetable.setFrom(from);
        timetable.setTo(to);
        return timetable;
    }

    public TimetableResponseDTO generateTimetableForTeacher(Long teacherId, ZonedDateTime from, ZonedDateTime to) {
        List<Lesson> lessons = lessonRepository.findAllByGroupIdAndStartBetweenFromAndTo(teacherId, from, to);
        TimetableResponseDTO timetable = new TimetableResponseDTO();
        List<DayDTO> days = TimetableUtils.groupLessonsByDaysForTeacher(lessons, from, to);
        timetable.setDays(days);
        timetable.setFrom(from);
        timetable.setTo(to);
        return timetable;
    }
}
