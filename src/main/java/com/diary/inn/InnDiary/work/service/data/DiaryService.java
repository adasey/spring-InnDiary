package com.diary.inn.InnDiary.work.service.data;

import com.diary.inn.InnDiary.work.domain.Diary;
import com.diary.inn.InnDiary.work.domain.Slot;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
