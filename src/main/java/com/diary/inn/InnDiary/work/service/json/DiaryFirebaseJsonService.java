package com.diary.inn.InnDiary.work.service.json;

import com.diary.inn.InnDiary.login.domain.User;
import com.diary.inn.InnDiary.work.domain.DiaryMeta;
import com.diary.inn.InnDiary.work.domain.Slot;

import java.util.List;
import java.util.Map;

public interface DiaryFirebaseJsonService {
    List<Slot> processAllSlotFromFirebaseData(User u);
    Map<Integer, List<DiaryMeta>> getDiaryWithSlot();
    List<Integer> isAnySlotHasData();
}
