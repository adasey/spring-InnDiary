package com.diary.inn.InnDiary.utils.firebase;

import com.google.firebase.database.DatabaseReference;
import org.springframework.stereotype.Component;

@Component
public class FirebaseFetcher {
    private final FirebaseData firebaseData = new FirebaseData();

    public void fetchFirebaseData(DatabaseReference databaseReference) {
        this.firebaseData.setDatabaseReference(databaseReference);
    }

    public FirebaseData loadFirebaseRef() {
        return this.firebaseData;
    }
}
