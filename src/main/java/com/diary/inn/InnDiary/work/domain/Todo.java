package com.diary.inn.InnDiary.work.domain;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
    private Long tSeq;

    private Long todoSeq;
    private String title;
    private LocalDate date;
    private String content;

    private Long slotSeq;
}
