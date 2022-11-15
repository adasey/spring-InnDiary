package com.diary.inn.InnDiary.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateParser {
    public static LocalDate stringToLocalDate(String time) {
        LocalDate ldt = null;

        if (time.length() > 8) {
            ldt = LocalDate.parse(time, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
        }
        else {
            ldt = LocalDate.parse(time, DateTimeFormatter.ofPattern("yyyyMMdd"));
        }

        return ldt;
    }
}
