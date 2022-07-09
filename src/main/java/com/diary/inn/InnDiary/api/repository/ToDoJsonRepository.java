package com.diary.inn.InnDiary.api.repository;

import com.diary.inn.InnDiary.api.entity.ToDoJsonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoJsonRepository extends JpaRepository<ToDoJsonEntity, Long> {
    @Query("select t from ToDoJsonEntity t where t.user = :email")
    ToDoJsonEntity findByLoginId(@Param("email") String user);
}
