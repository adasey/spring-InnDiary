package com.diary.inn.InnDiary.work.domain.info;

import lombok.*;

import java.time.LocalDateTime;

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
    private String diarySaveTitle;
    private LocalDateTime diarySaveDate;
}
