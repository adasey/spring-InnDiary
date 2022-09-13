package com.diary.inn.InnDiary.work.service.data;

import com.diary.inn.InnDiary.work.domain.Diary;
import com.diary.inn.InnDiary.work.domain.Slot;

import java.util.List;

public interface DiaryService {
    Diary createDiary(Diary diary);
    Diary findDiaryBySlotNSeq(Slot slot, Long diarySeq);
    List<Diary> findAllDiaryBySlot(Slot slot);
    void updateDiary(Diary diary);
    void deleteDiary(Diary diary);
    void deleteDiaryBySlot(Long slotSeq);
}
