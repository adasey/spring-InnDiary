package com.diary.inn.InnDiary.work.repository.api;

import com.diary.inn.InnDiary.work.entity.api.ToDoJsonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoJsonRepository extends JpaRepository<ToDoJsonEntity, Long> {
}
