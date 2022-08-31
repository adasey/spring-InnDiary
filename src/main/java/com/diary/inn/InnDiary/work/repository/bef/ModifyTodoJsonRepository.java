package com.diary.inn.InnDiary.work.repository.bef;

import com.diary.inn.InnDiary.login.entity.UserEntity;
import com.diary.inn.InnDiary.work.entity.bef.DiaryJsonEntity;
import com.diary.inn.InnDiary.work.entity.bef.TodoJsonEntity;

import java.util.List;

public interface ModifyTodoJsonRepository {
    void setInitFirebaseJson(Object fj);
    List<DiaryJsonEntity> findDataFromFirebase(UserEntity user);
    List<TodoJsonEntity> allSlotFoundByToDoSlot(String toDoSlot);
    TodoJsonEntity slotFoundBySlotNum(String toDoSlot, int slotNum);
}
