package com.diary.inn.InnDiary.work.repository;

import com.diary.inn.InnDiary.work.entity.SlotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlotRepository extends JpaRepository<SlotEntity, Long> {
}
