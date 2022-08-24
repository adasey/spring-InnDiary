package com.diary.inn.InnDiary.work.repository.info;

import com.diary.inn.InnDiary.work.entity.info.ToDoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Map;

public interface ToDoRepository extends JpaRepository<ToDoEntity, Long> {
}
