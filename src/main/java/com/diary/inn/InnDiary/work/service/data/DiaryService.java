package com.diary.inn.InnDiary.work.service.data;

import com.diary.inn.InnDiary.work.domain.Diary;
import com.diary.inn.InnDiary.work.domain.Slot;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface DiaryService {
    Diary createDiary(Diary diary);
    Diary findDiaryBySlotNSeq(Slot slot, Long diarySeq);
    List<Diary> findAllDiaryBySlot(Slot slot);
    List<Diary> findMonthDiary(LocalDate date);
    Map<LocalDate, List<Diary>> findSixMonthDiary(LocalDate date);
    Map<LocalDate, List<Diary>> findBetweenMonthDiary(LocalDate startDate, LocalDate endDate);
    void updateDiary(Diary diary);
    void deleteDiary(Diary diary);
    void deleteDiaryBySlot(Long slotSeq);
}
