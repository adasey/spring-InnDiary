package com.diary.inn.InnDiary.work.domain.info;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DiaryInfo {
    private Long seq;
    private String title;
    private String date;
    private int weather;
    private int status;
    private String content;
}
