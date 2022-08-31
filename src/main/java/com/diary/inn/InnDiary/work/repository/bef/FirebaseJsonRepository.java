package com.diary.inn.InnDiary.work.repository.bef;

import com.diary.inn.InnDiary.work.domain.bef.Slot;
import com.google.firebase.database.DataSnapshot;

public interface FirebaseJsonRepository {
    Object getValueChoose(Object val);
    void setInitFirebaseJson(DataSnapshot fb);
    void setDataByUid(DataSnapshot fb);
    DataSnapshot getDataByUid(String uid);
    Slot getDiaryOrTodoListOfData(String which, String slotNum);
}
