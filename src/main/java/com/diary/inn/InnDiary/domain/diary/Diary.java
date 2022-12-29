package com.diary.inn.InnDiary.domain.diary;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Diary {
    private Long dSeq;

    private Long diarySeq;
    private String title;
    private LocalDate date;
    private Integer weather;
    private Integer status;
    private String content;

    private Long slotSeq;
}
