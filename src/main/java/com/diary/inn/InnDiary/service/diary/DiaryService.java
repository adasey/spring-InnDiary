package com.diary.inn.InnDiary.service.diary;

import com.diary.inn.InnDiary.domain.diary.Diary;
import com.diary.inn.InnDiary.domain.diary.Slot;

import java.time.LocalDate;
import java.util.List;

public interface DiaryService {
    void setWantSlot(Slot slot);
    Slot getNowSlot();
    boolean isSlotSetting();

    Diary createDiary(Diary diary);
    Diary findDiaryBySeq(Long diarySeq);
    List<Diary> findAllDiaryBySlot();
    List<Diary> findMonthDiary(LocalDate date);
    List<Diary> findBetweenMonthDiary(LocalDate startDate, LocalDate endDate);
    void updateDiary(Diary diary);
    void deleteDiary(Diary diary);
    void deleteDiaryBySlot(Long slotSeq);
}
