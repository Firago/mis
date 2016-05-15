package com.dfirago.mis.repository;

import com.dfirago.mis.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Spring Data JPA repository for the Lesson entity.
 */
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query("select distinct lesson from Lesson lesson left join fetch lesson.studentGroups")
    List<Lesson> findAllWithEagerRelationships();

    @Query("select lesson from Lesson lesson left join fetch lesson.studentGroups where lesson.id =:id")
    Lesson findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select lesson from Lesson lesson where :groupId = lesson.subjectEntry.studentGroup.id and lesson.start between :from and :to order by lesson.start asc")
    List<Lesson> findAllByGroupIdAndStartBetweenFromAndTo(
        @Param("groupId") Long groupId,
        @Param("from") ZonedDateTime from,
        @Param("to") ZonedDateTime to);

    @Query("select lesson from Lesson lesson where :teacherId = lesson.teacher.id and lesson.start between :from and :to order by lesson.start asc")
    List<Lesson> findAllByTeacherIdAndStartBetweenFromAndTo(
        @Param("teacherId") Long teacherId,
        @Param("from") ZonedDateTime from,
        @Param("to") ZonedDateTime to);
}
