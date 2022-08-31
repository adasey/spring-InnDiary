package com.diary.inn.InnDiary.work.repository.bef;

import com.diary.inn.InnDiary.work.domain.bef.Slot;
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

    private DataSnapshot firebase;
    private DataSnapshot dataByUid;
    private String refactor;

    @Override
    public void setInitFirebaseJson(DataSnapshot firebase) {
        this.firebase = firebase;
    }

    @Override
    public void setDataByUid(DataSnapshot fb) {
        this.dataByUid = fb;
    }

    @Override
    public DataSnapshot getDataByUid(String uid) {
        return firebase.child(uid);
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
    public Slot getDiaryOrTodoListOfData(String which, String slotNum) {
        return dataByUid.child(which).child(slotNum).getValue(Slot.class);
    }
}
