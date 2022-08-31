package com.diary.inn.InnDiary.work.domain.bef;

import lombok.*;

import java.util.HashMap;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DataSlot {
    private HashMap<String, Object> diary_slot;
    private HashMap<String, Object> todo_slot;
}
