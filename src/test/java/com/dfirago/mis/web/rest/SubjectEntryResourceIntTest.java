package com.dfirago.mis.web.rest;

import com.dfirago.mis.Application;
import com.dfirago.mis.domain.SubjectEntry;
import com.dfirago.mis.repository.SubjectEntryRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the SubjectEntryResource REST controller.
 *
 * @see SubjectEntryResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SubjectEntryResourceIntTest {


    @Inject
    private SubjectEntryRepository subjectEntryRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSubjectEntryMockMvc;

    private SubjectEntry subjectEntry;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SubjectEntryResource subjectEntryResource = new SubjectEntryResource();
        ReflectionTestUtils.setField(subjectEntryResource, "subjectEntryRepository", subjectEntryRepository);
        this.restSubjectEntryMockMvc = MockMvcBuilders.standaloneSetup(subjectEntryResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        subjectEntry = new SubjectEntry();
    }

    @Test
    @Transactional
    public void createSubjectEntry() throws Exception {
        int databaseSizeBeforeCreate = subjectEntryRepository.findAll().size();

        // Create the SubjectEntry

        restSubjectEntryMockMvc.perform(post("/api/subjectEntrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subjectEntry)))
                .andExpect(status().isCreated());

        // Validate the SubjectEntry in the database
        List<SubjectEntry> subjectEntrys = subjectEntryRepository.findAll();
        assertThat(subjectEntrys).hasSize(databaseSizeBeforeCreate + 1);
        SubjectEntry testSubjectEntry = subjectEntrys.get(subjectEntrys.size() - 1);
    }

    @Test
    @Transactional
    public void getAllSubjectEntrys() throws Exception {
        // Initialize the database
        subjectEntryRepository.saveAndFlush(subjectEntry);

        // Get all the subjectEntrys
        restSubjectEntryMockMvc.perform(get("/api/subjectEntrys?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(subjectEntry.getId().intValue())));
    }

    @Test
    @Transactional
    public void getSubjectEntry() throws Exception {
        // Initialize the database
        subjectEntryRepository.saveAndFlush(subjectEntry);

        // Get the subjectEntry
        restSubjectEntryMockMvc.perform(get("/api/subjectEntrys/{id}", subjectEntry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(subjectEntry.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSubjectEntry() throws Exception {
        // Get the subjectEntry
        restSubjectEntryMockMvc.perform(get("/api/subjectEntrys/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubjectEntry() throws Exception {
        // Initialize the database
        subjectEntryRepository.saveAndFlush(subjectEntry);

		int databaseSizeBeforeUpdate = subjectEntryRepository.findAll().size();

        // Update the subjectEntry

        restSubjectEntryMockMvc.perform(put("/api/subjectEntrys")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subjectEntry)))
                .andExpect(status().isOk());

        // Validate the SubjectEntry in the database
        List<SubjectEntry> subjectEntrys = subjectEntryRepository.findAll();
        assertThat(subjectEntrys).hasSize(databaseSizeBeforeUpdate);
        SubjectEntry testSubjectEntry = subjectEntrys.get(subjectEntrys.size() - 1);
    }

    @Test
    @Transactional
    public void deleteSubjectEntry() throws Exception {
        // Initialize the database
        subjectEntryRepository.saveAndFlush(subjectEntry);

		int databaseSizeBeforeDelete = subjectEntryRepository.findAll().size();

        // Get the subjectEntry
        restSubjectEntryMockMvc.perform(delete("/api/subjectEntrys/{id}", subjectEntry.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<SubjectEntry> subjectEntrys = subjectEntryRepository.findAll();
        assertThat(subjectEntrys).hasSize(databaseSizeBeforeDelete - 1);
    }
}
