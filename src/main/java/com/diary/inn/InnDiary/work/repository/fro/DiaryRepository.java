package com.diary.inn.InnDiary.work.repository.fro;

import com.diary.inn.InnDiary.work.entity.fro.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {
}
