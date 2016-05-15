package com.dfirago.mis.domain.enumeration;

/**
 * The SubjectType enumeration.
 */
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
