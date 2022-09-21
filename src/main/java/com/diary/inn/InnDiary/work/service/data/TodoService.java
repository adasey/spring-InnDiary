package com.diary.inn.InnDiary.work.service.data;

import com.diary.inn.InnDiary.work.domain.Slot;
import com.diary.inn.InnDiary.work.domain.Todo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
