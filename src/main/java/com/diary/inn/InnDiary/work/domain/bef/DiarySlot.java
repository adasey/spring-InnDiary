package com.diary.inn.InnDiary.work.domain.bef;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiarySlot {
    private String title;
    private String mod_date;
    private String diary_list;
}
