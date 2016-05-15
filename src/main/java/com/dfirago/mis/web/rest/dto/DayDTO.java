package com.dfirago.mis.web.rest.dto;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * Created by dmfi on 15/05/2016.
 */
public class DayDTO {

    private String title;
    private ZonedDateTime date;
    private List<LessonDTO> lessons;

    public DayDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public List<LessonDTO> getLessons() {
        return lessons;
    }

    public void setLessons(List<LessonDTO> lessons) {
        this.lessons = lessons;
    }

    @Override
    public String toString() {
        return "DayDTO{" +
            "title='" + title + '\'' +
            ", date=" + date +
            ", lessons=" + lessons +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DayDTO)) return false;

        DayDTO dayDTO = (DayDTO) o;

        if (title != null ? !title.equals(dayDTO.title) : dayDTO.title != null) return false;
        return date != null ? date.equals(dayDTO.date) : dayDTO.date == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
