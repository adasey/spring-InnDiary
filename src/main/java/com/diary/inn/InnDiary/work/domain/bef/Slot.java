package com.diary.inn.InnDiary.work.domain.bef;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Slot {
    private String title;
    private String mod_date;
    private String diary_list;
}
