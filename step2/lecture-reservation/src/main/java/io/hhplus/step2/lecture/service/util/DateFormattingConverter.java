package io.hhplus.step2.lecture.service.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormattingConverter {
    private static final DateTimeFormatter yyyyMMddHHFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH");

    public static String convert(LocalDateTime value) {
        return value.format(yyyyMMddHHFormatter);
    }
}
