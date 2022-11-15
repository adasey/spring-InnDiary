package com.diary.inn.InnDiary.repository.firebase;

import com.diary.inn.InnDiary.domain.login.User;
import com.diary.inn.InnDiary.utils.meta.DiaryMeta;
import com.diary.inn.InnDiary.domain.diary.Slot;

import java.util.List;
import java.util.Map;

public interface DiaryFirebaseJsonRepository {
    List<Slot> processAllSlotFromFirebaseData(User u);
    Map<Integer, List<DiaryMeta>> getDiaryWithSlot();
    List<Integer> isAnySlotHasData();
}
