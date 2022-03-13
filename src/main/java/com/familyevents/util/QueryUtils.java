package com.familyevents.util;

public class QueryUtils {
    public static String like(String property, String value) {
        return property + " LIKE CONCAT('%', '" + value + "', '%')";
    }
}
