package com.diary.inn.InnDiary.repository.diary;

import com.diary.inn.InnDiary.entity.diary.SlotEntity;
import com.diary.inn.InnDiary.entity.diary.TodoEntity;

import java.time.LocalDate;
import java.util.List;

public interface SearchTodoRepository {
    void setSlot(SlotEntity se);
    SlotEntity getSlot();
    boolean isSlotSetting();

    TodoEntity findBySeq(Long seq);
    List<TodoEntity> findAllBySlot();
    List<TodoEntity> findByMonthDate(LocalDate date);
    List<TodoEntity> findByBetweenMonthDate(LocalDate startDate, LocalDate endDate);
    void updateTodo(TodoEntity de);
    void deleteBySlot(Long seq);
}
