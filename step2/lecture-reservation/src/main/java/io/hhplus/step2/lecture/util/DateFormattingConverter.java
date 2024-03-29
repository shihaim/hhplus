package io.hhplus.step2.lecture.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormattingConverter {
    private static final DateTimeFormatter yyyyMMddHHFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");

    public static String convert(LocalDateTime value) {
        return value != null ? value.format(yyyyMMddHHFormatter) : null;
    }
}
