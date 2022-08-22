package com.diary.inn.InnDiary.work.domain.info;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ToDoInfo {
    private Long seq;
    private String title;
    private String date;
    private String content;
}
