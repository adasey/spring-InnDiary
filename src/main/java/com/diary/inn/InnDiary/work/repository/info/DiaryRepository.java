package com.diary.inn.InnDiary.work.repository.info;

import com.diary.inn.InnDiary.work.entity.info.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {
}
