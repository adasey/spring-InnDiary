package com.diary.inn.InnDiary.work.domain.fro;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoInfo {
    private Long seq;
    private String title;
    private String date;
    private String content;
}
