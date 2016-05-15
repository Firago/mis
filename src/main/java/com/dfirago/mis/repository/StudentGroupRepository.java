package com.dfirago.mis.repository;

import com.dfirago.mis.domain.StudentGroup;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the StudentGroup entity.
 */
public interface StudentGroupRepository extends JpaRepository<StudentGroup,Long> {

}
