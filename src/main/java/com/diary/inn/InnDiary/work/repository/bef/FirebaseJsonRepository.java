package com.diary.inn.InnDiary.work.repository.bef;

import com.diary.inn.InnDiary.login.entity.UserEntity;
import com.diary.inn.InnDiary.work.entity.bef.DiaryJsonEntity;
import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;
import java.util.List;

public interface FirebaseJsonRepository {
    Object getValueChoose(Object val);
    void setInitFirebaseJson(DataSnapshot fj);
    void setFirebaseDB(HashMap<String, Object> value);
    HashMap<String, Object> getFirebaseDB();
    void processingFirebaseJsonToMemoryDB();
    DataSnapshot getDataByUid(String uid);
}
