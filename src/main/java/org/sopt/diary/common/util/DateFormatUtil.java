package org.sopt.diary.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatUtil {
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    public static String format(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
        return dateTime.format(formatter);
    }
}
