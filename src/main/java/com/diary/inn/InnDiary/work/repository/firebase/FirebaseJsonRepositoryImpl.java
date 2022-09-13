package com.diary.inn.InnDiary.work.repository.firebase;

import com.google.firebase.database.DataSnapshot;
import org.springframework.stereotype.Repository;

@Repository
public class FirebaseJsonRepositoryImpl implements FirebaseJsonRepository {
    private DataSnapshot firebase;

    @Override
    public void setFirebaseData(DataSnapshot ds) {
        this.firebase = ds;
    }

    @Override
    public void setValueByUid(String uid) {
        this.firebase = this.firebase.child(uid);
    }

    @Override
    public boolean isDataFoundByUid() {
        return this.firebase.exists();
    }

    @Override
    public DataSnapshot getFirebaseData() {
        return this.firebase;
    }

    @Override
    public DataSnapshot getWhichData(String which) {
        if (firebase.hasChild(which)) {
            return firebase.child(which);
        }
        return null;
    }

    @Override
    public DataSnapshot getWhichSlotNumData(String which, String sNum) {
        if (getWhichData(which).hasChild(sNum)) {
            return getWhichData(which).child(sNum);
        }
        return null;
    }
}
