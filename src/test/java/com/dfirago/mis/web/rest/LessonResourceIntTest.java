package com.dfirago.mis.web.rest;

import com.dfirago.mis.Application;
import com.dfirago.mis.domain.Lesson;
import com.dfirago.mis.repository.LessonRepository;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the LessonResource REST controller.
 *
 * @see LessonResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class LessonResourceIntTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of("Z"));


    private static final ZonedDateTime DEFAULT_START = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneId.systemDefault());
    private static final ZonedDateTime UPDATED_START = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final String DEFAULT_START_STR = dateTimeFormatter.format(DEFAULT_START);

    private static final Integer DEFAULT_DURATION = 0;
    private static final Integer UPDATED_DURATION = 1;
    private static final String DEFAULT_ROOM = "AAAAA";
    private static final String UPDATED_ROOM = "BBBBB";

    @Inject
    private LessonRepository lessonRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restLessonMockMvc;

    private Lesson lesson;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LessonResource lessonResource = new LessonResource();
        ReflectionTestUtils.setField(lessonResource, "lessonRepository", lessonRepository);
        this.restLessonMockMvc = MockMvcBuilders.standaloneSetup(lessonResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        lesson = new Lesson();
        lesson.setStart(DEFAULT_START);
        lesson.setDuration(DEFAULT_DURATION);
        lesson.setRoom(DEFAULT_ROOM);
    }

    @Test
    @Transactional
    public void createLesson() throws Exception {
        int databaseSizeBeforeCreate = lessonRepository.findAll().size();

        // Create the Lesson

        restLessonMockMvc.perform(post("/api/lessons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lesson)))
                .andExpect(status().isCreated());

        // Validate the Lesson in the database
        List<Lesson> lessons = lessonRepository.findAll();
        assertThat(lessons).hasSize(databaseSizeBeforeCreate + 1);
        Lesson testLesson = lessons.get(lessons.size() - 1);
        assertThat(testLesson.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testLesson.getDuration()).isEqualTo(DEFAULT_DURATION);
        assertThat(testLesson.getRoom()).isEqualTo(DEFAULT_ROOM);
    }

    @Test
    @Transactional
    public void checkStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = lessonRepository.findAll().size();
        // set the field null
        lesson.setStart(null);

        // Create the Lesson, which fails.

        restLessonMockMvc.perform(post("/api/lessons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lesson)))
                .andExpect(status().isBadRequest());

        List<Lesson> lessons = lessonRepository.findAll();
        assertThat(lessons).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDurationIsRequired() throws Exception {
        int databaseSizeBeforeTest = lessonRepository.findAll().size();
        // set the field null
        lesson.setDuration(null);

        // Create the Lesson, which fails.

        restLessonMockMvc.perform(post("/api/lessons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lesson)))
                .andExpect(status().isBadRequest());

        List<Lesson> lessons = lessonRepository.findAll();
        assertThat(lessons).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLessons() throws Exception {
        // Initialize the database
        lessonRepository.saveAndFlush(lesson);

        // Get all the lessons
        restLessonMockMvc.perform(get("/api/lessons?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(lesson.getId().intValue())))
                .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START_STR)))
                .andExpect(jsonPath("$.[*].duration").value(hasItem(DEFAULT_DURATION)))
                .andExpect(jsonPath("$.[*].room").value(hasItem(DEFAULT_ROOM.toString())));
    }

    @Test
    @Transactional
    public void getLesson() throws Exception {
        // Initialize the database
        lessonRepository.saveAndFlush(lesson);

        // Get the lesson
        restLessonMockMvc.perform(get("/api/lessons/{id}", lesson.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(lesson.getId().intValue()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START_STR))
            .andExpect(jsonPath("$.duration").value(DEFAULT_DURATION))
            .andExpect(jsonPath("$.room").value(DEFAULT_ROOM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLesson() throws Exception {
        // Get the lesson
        restLessonMockMvc.perform(get("/api/lessons/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLesson() throws Exception {
        // Initialize the database
        lessonRepository.saveAndFlush(lesson);

		int databaseSizeBeforeUpdate = lessonRepository.findAll().size();

        // Update the lesson
        lesson.setStart(UPDATED_START);
        lesson.setDuration(UPDATED_DURATION);
        lesson.setRoom(UPDATED_ROOM);

        restLessonMockMvc.perform(put("/api/lessons")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(lesson)))
                .andExpect(status().isOk());

        // Validate the Lesson in the database
        List<Lesson> lessons = lessonRepository.findAll();
        assertThat(lessons).hasSize(databaseSizeBeforeUpdate);
        Lesson testLesson = lessons.get(lessons.size() - 1);
        assertThat(testLesson.getStart()).isEqualTo(UPDATED_START);
        assertThat(testLesson.getDuration()).isEqualTo(UPDATED_DURATION);
        assertThat(testLesson.getRoom()).isEqualTo(UPDATED_ROOM);
    }

    @Test
    @Transactional
    public void deleteLesson() throws Exception {
        // Initialize the database
        lessonRepository.saveAndFlush(lesson);

		int databaseSizeBeforeDelete = lessonRepository.findAll().size();

        // Get the lesson
        restLessonMockMvc.perform(delete("/api/lessons/{id}", lesson.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Lesson> lessons = lessonRepository.findAll();
        assertThat(lessons).hasSize(databaseSizeBeforeDelete - 1);
    }
}
