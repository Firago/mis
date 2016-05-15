package com.dfirago.mis.util;

/**
 * Created by dmfi on 15/05/2016.
 */
public class StringUtils {

    public static String capitalize(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
