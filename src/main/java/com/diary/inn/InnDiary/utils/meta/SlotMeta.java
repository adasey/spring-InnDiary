package com.diary.inn.InnDiary.utils.meta;

import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SlotMeta {
    private String title;
    private String modDate;
    private List<Object> diaryList;
}
