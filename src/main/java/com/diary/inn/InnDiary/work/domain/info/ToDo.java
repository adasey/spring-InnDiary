package com.diary.inn.InnDiary.work.domain.info;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ToDo {
    private Long seq;

    private Long toDoId;
    private String title;
    private LocalDateTime toDoDate;
    private String content;

    private Long toDoJsonSeq;
    private String toDoSaveTitle;
    private LocalDateTime toDoSaveDate;
}
