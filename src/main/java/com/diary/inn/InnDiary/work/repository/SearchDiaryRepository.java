package com.diary.inn.InnDiary.work.repository;

import com.diary.inn.InnDiary.work.domain.Slot;
import com.diary.inn.InnDiary.work.entity.DiaryEntity;
import com.diary.inn.InnDiary.work.entity.SlotEntity;

import java.util.List;

public interface SearchDiaryRepository {
    DiaryEntity findBySlotNSeq(SlotEntity se, Long seq);
    List<DiaryEntity> findAllBySlot(SlotEntity se);
    void updateDiary(DiaryEntity de);
    void deleteBySlot(Long seq);
}
