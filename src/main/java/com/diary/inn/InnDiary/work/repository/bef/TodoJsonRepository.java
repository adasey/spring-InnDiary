package com.diary.inn.InnDiary.work.repository.bef;

import com.diary.inn.InnDiary.work.entity.bef.TodoJsonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoJsonRepository extends JpaRepository<TodoJsonEntity, Long> {
}
