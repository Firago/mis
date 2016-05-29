package com.dfirago.mis.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dfirago.mis.domain.StudentGroup;
import com.dfirago.mis.repository.StudentGroupRepository;
import com.dfirago.mis.service.TimetableService;
import com.dfirago.mis.web.rest.dto.TimetableRequestDTO;
import com.dfirago.mis.web.rest.dto.TimetableResponseDTO;
import com.dfirago.mis.web.rest.util.HeaderUtil;
import com.dfirago.mis.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing StudentGroup.
 */
@RestController
@RequestMapping("/api")
public class StudentGroupResource {

    private final Logger log = LoggerFactory.getLogger(StudentGroupResource.class);

    @Inject
    private StudentGroupRepository studentGroupRepository;
    @Inject
    private TimetableService timetableService;

    /**
     * POST  /studentGroups -> Create a new studentGroup.
     */
    @RequestMapping(value = "/studentGroups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StudentGroup> createStudentGroup(@Valid @RequestBody StudentGroup studentGroup) throws URISyntaxException {
        log.debug("REST request to save StudentGroup : {}", studentGroup);
        if (studentGroup.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("studentGroup", "idexists", "A new studentGroup cannot already have an ID")).body(null);
        }
        StudentGroup result = studentGroupRepository.save(studentGroup);
        return ResponseEntity.created(new URI("/api/studentGroups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("studentGroup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /studentGroups -> Updates an existing studentGroup.
     */
    @RequestMapping(value = "/studentGroups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StudentGroup> updateStudentGroup(@Valid @RequestBody StudentGroup studentGroup) throws URISyntaxException {
        log.debug("REST request to update StudentGroup : {}", studentGroup);
        if (studentGroup.getId() == null) {
            return createStudentGroup(studentGroup);
        }
        StudentGroup result = studentGroupRepository.save(studentGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("studentGroup", studentGroup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /studentGroups -> get all the studentGroups.
     */
    @RequestMapping(value = "/studentGroups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<StudentGroup>> getAllStudentGroups(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of StudentGroups");
        Page<StudentGroup> page = studentGroupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/studentGroups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /studentGroups/:id -> get the "id" studentGroup.
     */
    @RequestMapping(value = "/studentGroups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StudentGroup> getStudentGroup(@PathVariable Long id) {
        log.debug("REST request to get StudentGroup : {}", id);
        StudentGroup studentGroup = studentGroupRepository.findOne(id);
        return Optional.ofNullable(studentGroup)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /studentGroups/:id -> delete the "id" studentGroup.
     */
    @RequestMapping(value = "/studentGroups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStudentGroup(@PathVariable Long id) {
        log.debug("REST request to delete StudentGroup : {}", id);
        studentGroupRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("studentGroup", id.toString())).build();
    }

    /**
     * POST  /studentGroups/:id/timetable -> Retrieve timetable for studentGroup with id={id}
     */
    @RequestMapping(value = "/studentGroups/{id}/timetable",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimetableResponseDTO> getTimetableForGroup(@PathVariable Long id, @Valid @RequestBody TimetableRequestDTO request) throws URISyntaxException {
        log.debug("REST request to get timetable for StudentGroup : {}", request);
        TimetableResponseDTO response = timetableService
            .generateTimetableForGroup(id, request.getFrom(), request.getTo());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
