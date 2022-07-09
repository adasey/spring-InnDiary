package com.diary.inn.InnDiary.info.domain;

import lombok.*;

@Setter
@Getter
@Builder
public class Diary {
    private String title;
    private String date;
    private Integer weather;
    private Integer status;
    private String content;
}
