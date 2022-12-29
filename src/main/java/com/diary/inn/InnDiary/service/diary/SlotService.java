package com.diary.inn.InnDiary.service.diary;

import com.diary.inn.InnDiary.domain.login.User;
import com.diary.inn.InnDiary.domain.diary.Slot;

import java.util.List;

public interface SlotService {
    Slot createSlot(Slot slot);
    Slot findWhichSlotByNum(User user, String which, Integer num);
    List<Slot> findWhichSlot(User user, String which);
    List<Slot> findWhichSlotByUid(String uid, String which);
    List<Slot> findAllSlotByUser(User user);
    void updateBySlotNum(Slot slot);
    void deleteSlot(Slot slot);
}
