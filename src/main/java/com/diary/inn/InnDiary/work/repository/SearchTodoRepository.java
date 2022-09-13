package com.diary.inn.InnDiary.work.repository;

import com.diary.inn.InnDiary.work.entity.SlotEntity;
import com.diary.inn.InnDiary.work.entity.TodoEntity;

import java.util.List;

public interface SearchTodoRepository {
    TodoEntity findBySlotNSeq(SlotEntity se, Long seq);
    List<TodoEntity> findAllBySlot(SlotEntity se);
    void updateTodo(TodoEntity de);
    void deleteBySlot(Long seq);
}
