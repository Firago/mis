package com.dfirago.mis.service;

import com.dfirago.mis.domain.Lesson;
import com.dfirago.mis.domain.util.TimetableUtils;
import com.dfirago.mis.repository.LessonRepository;
import com.dfirago.mis.web.rest.dto.DayDTO;
import com.dfirago.mis.web.rest.dto.LessonDTO;
import com.dfirago.mis.web.rest.dto.TimetableResponseDTO;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

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
        Map<DayDTO, List<Lesson>> lessonsPerDay = TimetableUtils.groupLessonsByDays(lessons);
        for (DayDTO dayDTO : lessonsPerDay.keySet()) {
            List<LessonDTO> lessonDTOList = TimetableUtils.convertLessonToDtoWithTeacher(lessonsPerDay.get(dayDTO));
            dayDTO.setLessons(lessonDTOList);
        }
        timetable.setDays(lessonsPerDay.keySet());
        timetable.setFrom(from);
        timetable.setTo(to);
        return timetable;
    }

    public TimetableResponseDTO generateTimetableForTeacher(Long teacherId, ZonedDateTime from, ZonedDateTime to) {
        List<Lesson> lessons = lessonRepository.findAllByGroupIdAndStartBetweenFromAndTo(teacherId, from, to);
        TimetableResponseDTO timetable = new TimetableResponseDTO();
        Map<DayDTO, List<Lesson>> lessonsPerDay = TimetableUtils.groupLessonsByDays(lessons);
        for (DayDTO dayDTO : lessonsPerDay.keySet()) {
            List<LessonDTO> lessonDTOList = TimetableUtils.convertLessonToDtoWithGroup(lessonsPerDay.get(dayDTO));
            dayDTO.setLessons(lessonDTOList);
        }
        timetable.setDays(lessonsPerDay.keySet());
        timetable.setFrom(from);
        timetable.setTo(to);
        return timetable;
    }
}
