package com.dfirago.mis.repository;

import com.dfirago.mis.domain.Subject;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Subject entity.
 */
public interface SubjectRepository extends JpaRepository<Subject,Long> {

}
