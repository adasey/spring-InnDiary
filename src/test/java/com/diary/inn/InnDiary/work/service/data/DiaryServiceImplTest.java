package com.diary.inn.InnDiary.work.service.data;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
@Slf4j
class DiaryServiceImplTest {
    @Autowired
    DiaryConvertService diaryConvertService;
    @Autowired
    DiaryService diaryService;

    @Test
    void entityToDto() {
    }

    @Test
    void dtoToEntity() {
    }

    @Test
    void slotToEntity() {
    }

    @Test
    void createDiary() {
    }

    @Test
    void findDiaryBySlotNSeq() {
    }

    @Test
    void findAllDiaryBySlot() {
    }

    @Test
    void findMonthDiary() {
    }

    @Test
    void findSixMonthDiary() {
    }

    @Test
    void findBetweenMonthDiary() {
        diaryService.findBetweenMonthDiary(LocalDate.of(2022, 5, 3), LocalDate.now());
    }

    @Test
    void updateDiary() {
    }

    @Test
    void deleteDiary() {
    }

    @Test
    void deleteDiaryBySlot() {
    }
}