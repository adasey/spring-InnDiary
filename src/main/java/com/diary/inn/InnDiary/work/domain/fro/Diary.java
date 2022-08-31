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
public class Diary {
    private Long seq;

    private Long diaryId;
    private String title;
    private LocalDateTime diaryDate;
    private Integer weather;
    private Integer status;
    private String content;

    private Long diaryJsonSeq;
    private int diarySlotNum;
    private Date diarySaveDate;
}
