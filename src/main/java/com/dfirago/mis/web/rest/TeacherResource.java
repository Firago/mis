package com.dfirago.mis.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dfirago.mis.domain.Teacher;
import com.dfirago.mis.repository.TeacherRepository;
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
 * REST controller for managing Teacher.
 */
@RestController
@RequestMapping("/api")
public class TeacherResource {

    private final Logger log = LoggerFactory.getLogger(TeacherResource.class);

    @Inject
    private TeacherRepository teacherRepository;
    @Inject
    private TimetableService timetableService;

    /**
     * POST  /teachers -> Create a new teacher.
     */
    @RequestMapping(value = "/teachers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Teacher> createTeacher(@RequestBody Teacher teacher) throws URISyntaxException {
        log.debug("REST request to save Teacher : {}", teacher);
        if (teacher.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("teacher", "idexists", "A new teacher cannot already have an ID")).body(null);
        }
        Teacher result = teacherRepository.save(teacher);
        return ResponseEntity.created(new URI("/api/teachers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("teacher", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /teachers -> Updates an existing teacher.
     */
    @RequestMapping(value = "/teachers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Teacher> updateTeacher(@RequestBody Teacher teacher) throws URISyntaxException {
        log.debug("REST request to update Teacher : {}", teacher);
        if (teacher.getId() == null) {
            return createTeacher(teacher);
        }
        Teacher result = teacherRepository.save(teacher);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("teacher", teacher.getId().toString()))
            .body(result);
    }

    /**
     * GET  /teachers -> get all the teachers.
     */
    @RequestMapping(value = "/teachers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Teacher>> getAllTeachers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Teachers");
        Page<Teacher> page = teacherRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/teachers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /teachers/:id -> get the "id" teacher.
     */
    @RequestMapping(value = "/teachers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Teacher> getTeacher(@PathVariable Long id) {
        log.debug("REST request to get Teacher : {}", id);
        Teacher teacher = teacherRepository.findOne(id);
        return Optional.ofNullable(teacher)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /teachers/:id -> delete the "id" teacher.
     */
    @RequestMapping(value = "/teachers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        log.debug("REST request to delete Teacher : {}", id);
        teacherRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("teacher", id.toString())).build();
    }

    /**
     * POST  /teachers/:id/timetable -> Retrieve timetable for teachers with id={id}
     */
    @RequestMapping(value = "/teachers/{id}/timetable",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimetableResponseDTO> getTimetableForGroup(@PathVariable Long id, @Valid @RequestBody TimetableRequestDTO request) throws URISyntaxException {
        log.debug("REST request to get timetable for Teacher : {}", request);
        TimetableResponseDTO response = timetableService
            .generateTimetableForTeacher(id, request.getFrom(), request.getTo());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
