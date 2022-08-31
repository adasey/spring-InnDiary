package com.diary.inn.InnDiary.work.repository.bef;

import com.diary.inn.InnDiary.work.domain.bef.DiarySlot;
import com.google.firebase.database.DataSnapshot;

public interface FirebaseJsonRepository {
    void setInitFirebaseJson(DataSnapshot fb);
    void setDataByUid(DataSnapshot fb);
    DataSnapshot getDataByUid(String uid);
    Object getDiaryOrTodoListOfData(String which, String slotNum);
}
