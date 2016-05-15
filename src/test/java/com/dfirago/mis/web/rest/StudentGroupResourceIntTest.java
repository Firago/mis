package com.dfirago.mis.web.rest;

import com.dfirago.mis.Application;
import com.dfirago.mis.domain.StudentGroup;
import com.dfirago.mis.repository.StudentGroupRepository;

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
 * Test class for the StudentGroupResource REST controller.
 *
 * @see StudentGroupResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class StudentGroupResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    @Inject
    private StudentGroupRepository studentGroupRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restStudentGroupMockMvc;

    private StudentGroup studentGroup;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        StudentGroupResource studentGroupResource = new StudentGroupResource();
        ReflectionTestUtils.setField(studentGroupResource, "studentGroupRepository", studentGroupRepository);
        this.restStudentGroupMockMvc = MockMvcBuilders.standaloneSetup(studentGroupResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        studentGroup = new StudentGroup();
        studentGroup.setName(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createStudentGroup() throws Exception {
        int databaseSizeBeforeCreate = studentGroupRepository.findAll().size();

        // Create the StudentGroup

        restStudentGroupMockMvc.perform(post("/api/studentGroups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(studentGroup)))
                .andExpect(status().isCreated());

        // Validate the StudentGroup in the database
        List<StudentGroup> studentGroups = studentGroupRepository.findAll();
        assertThat(studentGroups).hasSize(databaseSizeBeforeCreate + 1);
        StudentGroup testStudentGroup = studentGroups.get(studentGroups.size() - 1);
        assertThat(testStudentGroup.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = studentGroupRepository.findAll().size();
        // set the field null
        studentGroup.setName(null);

        // Create the StudentGroup, which fails.

        restStudentGroupMockMvc.perform(post("/api/studentGroups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(studentGroup)))
                .andExpect(status().isBadRequest());

        List<StudentGroup> studentGroups = studentGroupRepository.findAll();
        assertThat(studentGroups).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStudentGroups() throws Exception {
        // Initialize the database
        studentGroupRepository.saveAndFlush(studentGroup);

        // Get all the studentGroups
        restStudentGroupMockMvc.perform(get("/api/studentGroups?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(studentGroup.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getStudentGroup() throws Exception {
        // Initialize the database
        studentGroupRepository.saveAndFlush(studentGroup);

        // Get the studentGroup
        restStudentGroupMockMvc.perform(get("/api/studentGroups/{id}", studentGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(studentGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingStudentGroup() throws Exception {
        // Get the studentGroup
        restStudentGroupMockMvc.perform(get("/api/studentGroups/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStudentGroup() throws Exception {
        // Initialize the database
        studentGroupRepository.saveAndFlush(studentGroup);

		int databaseSizeBeforeUpdate = studentGroupRepository.findAll().size();

        // Update the studentGroup
        studentGroup.setName(UPDATED_NAME);

        restStudentGroupMockMvc.perform(put("/api/studentGroups")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(studentGroup)))
                .andExpect(status().isOk());

        // Validate the StudentGroup in the database
        List<StudentGroup> studentGroups = studentGroupRepository.findAll();
        assertThat(studentGroups).hasSize(databaseSizeBeforeUpdate);
        StudentGroup testStudentGroup = studentGroups.get(studentGroups.size() - 1);
        assertThat(testStudentGroup.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void deleteStudentGroup() throws Exception {
        // Initialize the database
        studentGroupRepository.saveAndFlush(studentGroup);

		int databaseSizeBeforeDelete = studentGroupRepository.findAll().size();

        // Get the studentGroup
        restStudentGroupMockMvc.perform(delete("/api/studentGroups/{id}", studentGroup.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<StudentGroup> studentGroups = studentGroupRepository.findAll();
        assertThat(studentGroups).hasSize(databaseSizeBeforeDelete - 1);
    }
}
