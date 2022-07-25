package com.diary.inn.InnDiary.work.repository.api;

import com.diary.inn.InnDiary.work.entity.api.DiaryJsonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryJsonRepository extends JpaRepository<DiaryJsonEntity, Long> {
}
