package com.diary.inn.InnDiary.work.domain.info;

import lombok.*;

@Setter
@Getter
@Builder
public class ToDo {
    private String title;
    private String date;
    private Integer weather;
    private Integer status;
    private String content;
}
