package com.dfirago.mis.repository;

import com.dfirago.mis.domain.Teacher;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Teacher entity.
 */
public interface TeacherRepository extends JpaRepository<Teacher,Long> {

    @Query("select distinct teacher from Teacher teacher left join fetch teacher.subjects")
    List<Teacher> findAllWithEagerRelationships();

    @Query("select teacher from Teacher teacher left join fetch teacher.subjects where teacher.id =:id")
    Teacher findOneWithEagerRelationships(@Param("id") Long id);

}
