package com.diary.inn.InnDiary.repository.firebase;

import com.diary.inn.InnDiary.domain.login.User;
import com.diary.inn.InnDiary.domain.diary.Slot;
import com.diary.inn.InnDiary.utils.TodoMeta;

import java.util.List;
import java.util.Map;

public interface TodoFirebaseJsonRepository {
    List<Slot> processAllSlotFromFirebaseData(User u);
    Map<Integer, List<TodoMeta>> getTodoWithSlot();
    List<Integer> isAnySlotHasData();
}
