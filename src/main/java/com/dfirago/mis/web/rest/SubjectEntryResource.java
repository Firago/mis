package com.dfirago.mis.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.dfirago.mis.domain.SubjectEntry;
import com.dfirago.mis.repository.SubjectEntryRepository;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing SubjectEntry.
 */
@RestController
@RequestMapping("/api")
public class SubjectEntryResource {

    private final Logger log = LoggerFactory.getLogger(SubjectEntryResource.class);
        
    @Inject
    private SubjectEntryRepository subjectEntryRepository;
    
    /**
     * POST  /subjectEntrys -> Create a new subjectEntry.
     */
    @RequestMapping(value = "/subjectEntrys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SubjectEntry> createSubjectEntry(@RequestBody SubjectEntry subjectEntry) throws URISyntaxException {
        log.debug("REST request to save SubjectEntry : {}", subjectEntry);
        if (subjectEntry.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("subjectEntry", "idexists", "A new subjectEntry cannot already have an ID")).body(null);
        }
        SubjectEntry result = subjectEntryRepository.save(subjectEntry);
        return ResponseEntity.created(new URI("/api/subjectEntrys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("subjectEntry", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /subjectEntrys -> Updates an existing subjectEntry.
     */
    @RequestMapping(value = "/subjectEntrys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SubjectEntry> updateSubjectEntry(@RequestBody SubjectEntry subjectEntry) throws URISyntaxException {
        log.debug("REST request to update SubjectEntry : {}", subjectEntry);
        if (subjectEntry.getId() == null) {
            return createSubjectEntry(subjectEntry);
        }
        SubjectEntry result = subjectEntryRepository.save(subjectEntry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("subjectEntry", subjectEntry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /subjectEntrys -> get all the subjectEntrys.
     */
    @RequestMapping(value = "/subjectEntrys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SubjectEntry>> getAllSubjectEntrys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SubjectEntrys");
        Page<SubjectEntry> page = subjectEntryRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/subjectEntrys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /subjectEntrys/:id -> get the "id" subjectEntry.
     */
    @RequestMapping(value = "/subjectEntrys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SubjectEntry> getSubjectEntry(@PathVariable Long id) {
        log.debug("REST request to get SubjectEntry : {}", id);
        SubjectEntry subjectEntry = subjectEntryRepository.findOne(id);
        return Optional.ofNullable(subjectEntry)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /subjectEntrys/:id -> delete the "id" subjectEntry.
     */
    @RequestMapping(value = "/subjectEntrys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSubjectEntry(@PathVariable Long id) {
        log.debug("REST request to delete SubjectEntry : {}", id);
        subjectEntryRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("subjectEntry", id.toString())).build();
    }
}
