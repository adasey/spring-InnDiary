package com.diary.inn.InnDiary.work.service.data;

import com.diary.inn.InnDiary.work.domain.Slot;
import com.diary.inn.InnDiary.work.domain.Todo;

import java.util.List;

public interface TodoService {
    Todo createTodo(Todo todo);
    Todo findTodoBySlotNSeq(Slot slot, Long todoSeq);
    List<Todo> findAllTodoBySlot(Slot slot);
    void updateTodo(Todo todo);
    void deleteTodo(Todo todo);
    void deleteTodoBySlot(Long slotSeq);
}
