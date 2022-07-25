package com.diary.inn.InnDiary.work.domain.info;

import lombok.*;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Diary {
    private String title;
    private String regDate;
    private Integer weather;
    private Integer status;
    private String content;
}
