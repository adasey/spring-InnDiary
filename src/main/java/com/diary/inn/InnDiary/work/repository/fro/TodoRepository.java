package com.diary.inn.InnDiary.work.repository.fro;

import com.diary.inn.InnDiary.work.entity.fro.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
}
