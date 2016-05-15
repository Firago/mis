package com.dfirago.mis.web.rest.dto;

import java.time.ZonedDateTime;
import java.util.Set;

/**
 * Created by dmfi on 15/05/2016.
 */
public class TimetableResponseDTO {

    private ZonedDateTime from;
    private ZonedDateTime to;
    private Set<DayDTO> days;

    public TimetableResponseDTO() {
    }

    public ZonedDateTime getFrom() {
        return from;
    }

    public void setFrom(ZonedDateTime from) {
        this.from = from;
    }

    public ZonedDateTime getTo() {
        return to;
    }

    public void setTo(ZonedDateTime to) {
        this.to = to;
    }

    public Set<DayDTO> getDays() {
        return days;
    }

    public void setDays(Set<DayDTO> days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return "TimetableResponseDTO{" +
            "from=" + from +
            ", to=" + to +
            ", days=" + days +
            '}';
    }
}
