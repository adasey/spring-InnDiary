package com.diary.inn.InnDiary.work.domain.fro;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Todo {
    private Long seq;

    private Long toDoId;
    private String title;
    private LocalDateTime todoDate;
    private String content;

    private Long toDoJsonSeq;
    private int todoSlotNum;
    private Date todoSaveDate;
}
