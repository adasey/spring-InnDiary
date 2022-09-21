package com.diary.inn.InnDiary.work.repository;

import com.diary.inn.InnDiary.work.entity.DiaryEntity;
import com.diary.inn.InnDiary.work.entity.SlotEntity;
import com.diary.inn.InnDiary.work.entity.TodoEntity;

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
