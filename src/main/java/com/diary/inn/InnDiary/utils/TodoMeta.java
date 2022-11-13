package com.diary.inn.InnDiary.utils;

import lombok.*;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TodoMeta {
    private Long seq;
    private String title;
    private String date;
    private String content;
}
