package com.diary.inn.InnDiary.work.service.bef;

import com.diary.inn.InnDiary.work.domain.bef.DiaryJson;

public interface DiaryJsonService{
    Long join(DiaryJson diaryJson);
    void deleteDiarySlot(String diarySlot);
    void deleteDiarySlotByNum(String diarySlot, int slotNum);
}
