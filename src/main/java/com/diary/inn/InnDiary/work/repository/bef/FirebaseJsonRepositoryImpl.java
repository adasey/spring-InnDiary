package com.diary.inn.InnDiary.work.repository.bef;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DataSnapshot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
@Slf4j
public class FirebaseJsonRepositoryImpl implements FirebaseJsonRepository {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private HashMap<String, Object> fbDB;
    private DataSnapshot firebase;
    private String refactor;

    @Override
    public void setInitFirebaseJson(DataSnapshot firebase) {
        this.firebase = firebase;
    }

    @Override
    public void setFirebaseDB(HashMap<String, Object> value) {
        fbDB = value;
    }

    @Override
    public HashMap<String, Object> getFirebaseDB() {
        return fbDB;
    }

    // for test
    @Override
    public Object getValueChoose(Object val) {
        if (val.toString().equals("firebase")) {
            return firebase;
        }
        else {
            return refactor;
        }
    }

    @Override
    public void processingFirebaseJsonToMemoryDB() {

    }

    @Override
    public DataSnapshot getDataByUid(String uid) {
        log.info("value check of find : {}", firebase.child(uid));
        return firebase.child(uid);
    }
}
