package com.diary.inn.InnDiary.repository.diary;

import com.diary.inn.InnDiary.entity.diary.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {
}
