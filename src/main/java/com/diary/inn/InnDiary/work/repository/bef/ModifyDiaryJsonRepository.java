package com.diary.inn.InnDiary.work.repository.bef;

import com.diary.inn.InnDiary.login.entity.UserEntity;
import com.diary.inn.InnDiary.work.entity.bef.DiaryJsonEntity;

import java.util.List;

public interface ModifyDiaryJsonRepository {
    List<DiaryJsonEntity> allSlotFoundByDiarySlot(String diarySlot);
    DiaryJsonEntity slotFoundBySlotNum(String diarySlot, int slotNum);
}
