package com.diary.inn.InnDiary.domain.diary;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Slot {
    private Long seq;
    private String title;
    private int slotNum;
    private String which;
    private LocalDate modDate;

    private Long userSeq;
    private String userEmail;
    private String userName;
    private String userUid;
}
