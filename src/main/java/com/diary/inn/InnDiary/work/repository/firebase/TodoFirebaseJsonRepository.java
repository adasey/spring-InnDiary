package com.diary.inn.InnDiary.work.repository.firebase;

import com.diary.inn.InnDiary.login.domain.User;
import com.diary.inn.InnDiary.work.domain.Slot;
import com.diary.inn.InnDiary.work.domain.TodoMeta;

import java.util.List;
import java.util.Map;

public interface TodoFirebaseJsonRepository {
    List<Slot> processAllSlotFromFirebaseData(User u);
    Map<Integer, List<TodoMeta>> getTodoWithSlot();
    List<Integer> isAnySlotHasData();
}
