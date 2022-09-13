package com.diary.inn.InnDiary.work.repository.firebase;

import com.google.firebase.database.DataSnapshot;

public interface FirebaseJsonRepository {
    void setFirebaseData(DataSnapshot ds);
    void setValueByUid(String uid);
    boolean isDataFoundByUid();
    DataSnapshot getFirebaseData();
    DataSnapshot getWhichData(String which);
    DataSnapshot getWhichSlotNumData(String which, String sNum);
}
