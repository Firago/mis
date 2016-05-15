package com.dfirago.mis.repository;

import com.dfirago.mis.domain.Lesson;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Lesson entity.
 */
public interface LessonRepository extends JpaRepository<Lesson,Long> {

    @Query("select distinct lesson from Lesson lesson left join fetch lesson.studentGroups")
    List<Lesson> findAllWithEagerRelationships();

    @Query("select lesson from Lesson lesson left join fetch lesson.studentGroups where lesson.id =:id")
    Lesson findOneWithEagerRelationships(@Param("id") Long id);

}
