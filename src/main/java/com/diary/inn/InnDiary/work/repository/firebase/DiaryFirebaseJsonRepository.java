package com.diary.inn.InnDiary.work.repository.firebase;

import com.diary.inn.InnDiary.login.domain.User;
import com.diary.inn.InnDiary.work.domain.DiaryMeta;
import com.diary.inn.InnDiary.work.domain.Slot;

import java.util.List;
import java.util.Map;

public interface DiaryFirebaseJsonRepository {
    List<Slot> processAllSlotFromFirebaseData(User u);
    Map<Integer, List<DiaryMeta>> getDiaryWithSlot();
    List<Integer> isAnySlotHasData();
}
