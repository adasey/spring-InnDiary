package com.diary.inn.InnDiary.repository.diary;

import com.diary.inn.InnDiary.entity.diary.DiaryEntity;
import com.diary.inn.InnDiary.entity.diary.SlotEntity;

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
