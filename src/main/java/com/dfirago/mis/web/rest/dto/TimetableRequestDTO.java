package com.dfirago.mis.web.rest.dto;

import java.time.ZonedDateTime;

/**
 * Created by dmfi on 15/05/2016.
 */
public class TimetableRequestDTO {

    private ZonedDateTime from;
    private ZonedDateTime to;

    public TimetableRequestDTO() {
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

    @Override
    public String toString() {
        return "TimetableRequestDTO{" +
            "from=" + from +
            ", to=" + to +
            '}';
    }
}
