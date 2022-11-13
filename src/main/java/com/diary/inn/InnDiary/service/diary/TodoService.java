package com.diary.inn.InnDiary.service.diary;

import com.diary.inn.InnDiary.domain.diary.Slot;
import com.diary.inn.InnDiary.domain.diary.Todo;

import java.time.LocalDate;
import java.util.List;

public interface TodoService {
    void setWantSlot(Slot slot);
    Slot getNowSlot();
    boolean isSlotSetting();

    Todo createTodo(Todo todo);
    Todo findTodoBySeq(Long todoSeq);
    List<Todo> findAllTodoBySlot();
    List<Todo> findMonthTodo(LocalDate date);
    List<Todo> findBetweenMonthTodo(LocalDate startDate, LocalDate endDate);
    void updateTodo(Todo todo);
    void deleteTodo(Todo todo);
    void deleteTodoBySlot(Long slotSeq);
}
