package com.diary.inn.InnDiary.work.domain.bef;

import lombok.*;
import java.util.Date;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class TodoJson {
    private Long seq;
    private String diarySlot;
    private int slotNum;
    private Date modDate;
    private String todoList;
}
