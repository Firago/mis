package com.dfirago.mis.domain;

import java.time.ZonedDateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Lesson.
 */
@Entity
@Table(name = "lesson")
public class Lesson implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "start", nullable = false)
    private ZonedDateTime start;
    
    @NotNull
    @Min(value = 0)
    @Column(name = "duration", nullable = false)
    private Integer duration;
    
    @Column(name = "room")
    private String room;
    
    @ManyToOne
    @JoinColumn(name = "subject_entry_id")
    private SubjectEntry subjectEntry;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToMany
    @JoinTable(name = "lesson_student_group",
               joinColumns = @JoinColumn(name="lessons_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="student_groups_id", referencedColumnName="ID"))
    private Set<StudentGroup> studentGroups = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStart() {
        return start;
    }
    
    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public Integer getDuration() {
        return duration;
    }
    
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getRoom() {
        return room;
    }
    
    public void setRoom(String room) {
        this.room = room;
    }

    public SubjectEntry getSubjectEntry() {
        return subjectEntry;
    }

    public void setSubjectEntry(SubjectEntry subjectEntry) {
        this.subjectEntry = subjectEntry;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Set<StudentGroup> getStudentGroups() {
        return studentGroups;
    }

    public void setStudentGroups(Set<StudentGroup> studentGroups) {
        this.studentGroups = studentGroups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lesson lesson = (Lesson) o;
        if(lesson.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, lesson.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Lesson{" +
            "id=" + id +
            ", start='" + start + "'" +
            ", duration='" + duration + "'" +
            ", room='" + room + "'" +
            '}';
    }
}
