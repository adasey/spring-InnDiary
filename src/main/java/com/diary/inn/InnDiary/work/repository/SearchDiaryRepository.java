package com.diary.inn.InnDiary.work.repository;

import com.diary.inn.InnDiary.work.entity.DiaryEntity;
import com.diary.inn.InnDiary.work.entity.SlotEntity;

import java.time.LocalDate;
import java.util.List;

public interface SearchDiaryRepository {
    DiaryEntity findBySlotNSeq(SlotEntity se, Long seq);
    List<DiaryEntity> findAllBySlot(SlotEntity se);
    List<DiaryEntity> findByMonthDate(LocalDate date);
    List<DiaryEntity> findByBetweenMonthDate(LocalDate startDate, LocalDate endDate);
    void updateDiary(DiaryEntity de);
    void deleteBySlot(Long seq);
}
