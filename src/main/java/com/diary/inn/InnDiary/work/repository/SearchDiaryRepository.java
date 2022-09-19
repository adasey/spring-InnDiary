package com.diary.inn.InnDiary.work.repository;

import com.diary.inn.InnDiary.work.entity.DiaryEntity;
import com.diary.inn.InnDiary.work.entity.SlotEntity;

import java.time.LocalDate;
import java.util.List;

public interface SearchDiaryRepository {
    void setSlot(SlotEntity se);
    SlotEntity getSlot();
    boolean isSlotSetting();

    DiaryEntity findBySeq(Long seq);
    List<DiaryEntity> findAllBySlot();
    List<DiaryEntity> findByMonthDate(LocalDate date);
    List<DiaryEntity> findByBetweenMonthDate(LocalDate startDate, LocalDate endDate);
    void updateDiary(DiaryEntity de);
    void deleteBySlot(Long seq);
}
