package com.dfirago.mis.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * The SubjectType enumeration.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SubjectType {

    LECTURE("W"),
    PRACTICE("C"),
    LAB("L");

    private String shortName;

    SubjectType(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }
}
