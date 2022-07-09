package com.diary.inn.InnDiary.api.repository;

import com.diary.inn.InnDiary.api.entity.DiaryJsonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryJsonRepository extends JpaRepository<DiaryJsonEntity, Long> {
    @Query("select d from DiaryJsonEntity d where d.user = :email")
    DiaryJsonEntity findByLoginId(@Param("email") String user);
}
