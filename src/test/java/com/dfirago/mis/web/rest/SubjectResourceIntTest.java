package com.dfirago.mis.web.rest;

import com.dfirago.mis.Application;
import com.dfirago.mis.domain.Subject;
import com.dfirago.mis.repository.SubjectRepository;

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

import com.dfirago.mis.domain.enumeration.SubjectType;

/**
 * Test class for the SubjectResource REST controller.
 *
 * @see SubjectResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class SubjectResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";
    
    private static final SubjectType DEFAULT_TYPE = SubjectType.LECTURE;
    private static final SubjectType UPDATED_TYPE = SubjectType.PRACTICE;

    @Inject
    private SubjectRepository subjectRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restSubjectMockMvc;

    private Subject subject;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SubjectResource subjectResource = new SubjectResource();
        ReflectionTestUtils.setField(subjectResource, "subjectRepository", subjectRepository);
        this.restSubjectMockMvc = MockMvcBuilders.standaloneSetup(subjectResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        subject = new Subject();
        subject.setName(DEFAULT_NAME);
        subject.setType(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createSubject() throws Exception {
        int databaseSizeBeforeCreate = subjectRepository.findAll().size();

        // Create the Subject

        restSubjectMockMvc.perform(post("/api/subjects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subject)))
                .andExpect(status().isCreated());

        // Validate the Subject in the database
        List<Subject> subjects = subjectRepository.findAll();
        assertThat(subjects).hasSize(databaseSizeBeforeCreate + 1);
        Subject testSubject = subjects.get(subjects.size() - 1);
        assertThat(testSubject.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSubject.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = subjectRepository.findAll().size();
        // set the field null
        subject.setName(null);

        // Create the Subject, which fails.

        restSubjectMockMvc.perform(post("/api/subjects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subject)))
                .andExpect(status().isBadRequest());

        List<Subject> subjects = subjectRepository.findAll();
        assertThat(subjects).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = subjectRepository.findAll().size();
        // set the field null
        subject.setType(null);

        // Create the Subject, which fails.

        restSubjectMockMvc.perform(post("/api/subjects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subject)))
                .andExpect(status().isBadRequest());

        List<Subject> subjects = subjectRepository.findAll();
        assertThat(subjects).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSubjects() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get all the subjects
        restSubjectMockMvc.perform(get("/api/subjects?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(subject.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getSubject() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

        // Get the subject
        restSubjectMockMvc.perform(get("/api/subjects/{id}", subject.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(subject.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSubject() throws Exception {
        // Get the subject
        restSubjectMockMvc.perform(get("/api/subjects/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSubject() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

		int databaseSizeBeforeUpdate = subjectRepository.findAll().size();

        // Update the subject
        subject.setName(UPDATED_NAME);
        subject.setType(UPDATED_TYPE);

        restSubjectMockMvc.perform(put("/api/subjects")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(subject)))
                .andExpect(status().isOk());

        // Validate the Subject in the database
        List<Subject> subjects = subjectRepository.findAll();
        assertThat(subjects).hasSize(databaseSizeBeforeUpdate);
        Subject testSubject = subjects.get(subjects.size() - 1);
        assertThat(testSubject.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSubject.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void deleteSubject() throws Exception {
        // Initialize the database
        subjectRepository.saveAndFlush(subject);

		int databaseSizeBeforeDelete = subjectRepository.findAll().size();

        // Get the subject
        restSubjectMockMvc.perform(delete("/api/subjects/{id}", subject.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Subject> subjects = subjectRepository.findAll();
        assertThat(subjects).hasSize(databaseSizeBeforeDelete - 1);
    }
}
