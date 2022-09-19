package com.diary.inn.InnDiary.work.service.firebase;

import com.diary.inn.InnDiary.login.domain.User;
import com.diary.inn.InnDiary.work.domain.DiaryMeta;
import com.diary.inn.InnDiary.work.domain.Slot;
import com.diary.inn.InnDiary.work.domain.TodoMeta;
import com.google.firebase.database.DataSnapshot;

import java.util.List;
import java.util.Map;

public interface FirebaseService {
    void setDataSnap(DataSnapshot data);
    void setDataSnapByUid(String uid);
    Map<Integer, List<DiaryMeta>> getDiaryData();
    Map<Integer, List<TodoMeta>> getTodoData();
    List<Slot> processDiary(User u);
    List<Slot> processTodo(User u);
    List<Integer> diarySlotCheckList();
    List<Integer> todoSlotCheckList();

    void insertAllSlots(User u);
    void createAllSlotsData(User u);
    void createDiaryData(User u, int slotNum);
    void createTodoData(User u, int slotNum);
}
