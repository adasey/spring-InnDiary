package com.diary.inn.InnDiary.utils;

import lombok.*;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DiaryMeta {
    private Long seq;
    private String title;
    private String date;
    private int weather;
    private int status;
    private String content;
}
