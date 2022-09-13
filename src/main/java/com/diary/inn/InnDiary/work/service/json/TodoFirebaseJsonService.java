package com.diary.inn.InnDiary.work.service.json;

import com.diary.inn.InnDiary.login.domain.User;
import com.diary.inn.InnDiary.work.domain.DiaryMeta;
import com.diary.inn.InnDiary.work.domain.Slot;
import com.diary.inn.InnDiary.work.domain.TodoMeta;
import com.google.firebase.database.DataSnapshot;

import java.util.List;
import java.util.Map;

public interface TodoFirebaseJsonService {
    List<Slot> processAllSlotFromFirebaseData(User u);
    Map<Integer, List<TodoMeta>> getTodoWithSlot();
    List<Integer> isAnySlotHasData();
}
