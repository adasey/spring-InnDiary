package com.diary.inn.InnDiary.repository.diary;

import com.diary.inn.InnDiary.entity.diary.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {
}
