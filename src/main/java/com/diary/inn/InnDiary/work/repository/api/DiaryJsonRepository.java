package com.diary.inn.InnDiary.work.repository.api;

import com.diary.inn.InnDiary.work.entity.api.DiaryJsonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiaryJsonRepository extends JpaRepository<DiaryJsonEntity, Long> {
    @Query("select d from DiaryJsonEntity d where d.user = :email")
    List<DiaryJsonEntity> findByLoginId(@Param("email") String user);
}
