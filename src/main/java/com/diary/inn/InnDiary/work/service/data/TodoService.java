package com.diary.inn.InnDiary.work.service.data;

import com.diary.inn.InnDiary.work.domain.Slot;
import com.diary.inn.InnDiary.work.domain.Todo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TodoService {
    Todo createTodo(Todo todo);
    Todo findTodoBySlotNSeq(Slot slot, Long todoSeq);
    List<Todo> findAllTodoBySlot(Slot slot);
    List<Todo> findMonthTodo(LocalDate date);
    Map<LocalDate, List<Todo>> findSixMonthTodo(LocalDate date);
    Map<LocalDate, List<Todo>> findBetweenMonthTodo(LocalDate startDate, LocalDate endDate);
    void updateTodo(Todo todo);
    void deleteTodo(Todo todo);
    void deleteTodoBySlot(Long slotSeq);
}
