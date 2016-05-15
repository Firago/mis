package com.dfirago.mis.web.rest.dto;

/**
 * Created by dmfi on 15/05/2016.
 */
public class LessonDTO {

    private String title;
    private Integer start;
    private Integer end;
    private String with;
    private String room;
    private String type;

    public LessonDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public String getWith() {
        return with;
    }

    public void setWith(String with) {
        this.with = with;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "LessonDTO{" +
            "title='" + title + '\'' +
            ", start=" + start +
            ", end=" + end +
            ", with='" + with + '\'' +
            ", room='" + room + '\'' +
            ", type='" + type + '\'' +
            '}';
    }
}
