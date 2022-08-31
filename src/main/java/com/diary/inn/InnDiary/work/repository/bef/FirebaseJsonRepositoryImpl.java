package com.diary.inn.InnDiary.work.repository.bef;

import com.diary.inn.InnDiary.work.domain.bef.DiarySlot;
import com.diary.inn.InnDiary.work.domain.bef.TodoSlot;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DataSnapshot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
public class FirebaseJsonRepositoryImpl implements FirebaseJsonRepository {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private DataSnapshot firebase;
    private DataSnapshot dataByUid;

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

    @Override
    public Object getDiaryOrTodoListOfData(String which, String slotNum) {
        try {
            if (which.equals("diary_slot")) {
                return dataByUid.child(which).child(slotNum).getValue(DiarySlot.class);
            }
            else {
                return dataByUid.child(which).child(slotNum).getValue(TodoSlot.class);
            }
        } catch (IllegalArgumentException e) {
            log.info("wrong value, can't find data", e);
            return DiarySlot.builder().build();
        }
    }
}
