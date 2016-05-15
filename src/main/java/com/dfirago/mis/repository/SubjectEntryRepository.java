package com.dfirago.mis.repository;

import com.dfirago.mis.domain.SubjectEntry;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the SubjectEntry entity.
 */
public interface SubjectEntryRepository extends JpaRepository<SubjectEntry,Long> {

}
