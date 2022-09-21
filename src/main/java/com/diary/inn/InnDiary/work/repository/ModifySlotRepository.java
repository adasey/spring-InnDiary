package com.diary.inn.InnDiary.work.repository;

import com.diary.inn.InnDiary.login.domain.User;
import com.diary.inn.InnDiary.work.entity.SlotEntity;

import java.util.List;

public interface ModifySlotRepository {
    SlotEntity findWhichSlotByUserNNum(User user, String which, Integer num);
    List<SlotEntity> findWhichSlotByUser(User user, String which);
    List<SlotEntity> findWhichSlotByUid(String uid, String which);
    List<SlotEntity> findAllByUid(String uid);
    void updateSlotBySlotNum(SlotEntity se);
    void deleteByUser(Long seq);
    void deleteBySlot(SlotEntity slotEntity);
}
