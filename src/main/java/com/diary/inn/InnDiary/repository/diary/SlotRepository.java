package com.diary.inn.InnDiary.repository.diary;

import com.diary.inn.InnDiary.entity.diary.SlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlotRepository extends JpaRepository<SlotEntity, Long> {
}
