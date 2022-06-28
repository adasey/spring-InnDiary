package com.diary.inn.InnDiary.api.repository;

import com.diary.inn.InnDiary.api.entity.json.ToDoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoJsonRepository extends JpaRepository<ToDoEntity, Long> {
    @Query("select t from ToDoEntity t where t.user = :email")
    ToDoEntity findByLoginId(@Param("email") String user);
}
