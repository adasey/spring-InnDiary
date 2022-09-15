package com.diary.inn.InnDiary.work.repository;

import com.diary.inn.InnDiary.work.entity.DiaryEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class SearchDiaryRepositoryImplTest {
    @Autowired
    SearchDiaryRepository searchDiaryRepository;

//    @Test
//    void dateFuncTest() {
//        LocalDate startDate = LocalDate.of(2022,8,11);
//        LocalDate endDate = LocalDate.now();
//
//        Period period = Period.between(startDate, endDate);
//
//        log.info("value of between date : {}", startDate.lengthOfMonth());
//        log.info("value of between date : {}", period.getMonths());
//
//        List<DiaryEntity> byDate = searchDiaryRepository.findByDate(endDate);
//
//        log.info("test of find value by date : {}", byDate);
//    }

    @Test
    void findBySlotNSeq() {
    }

    @Test
    void findAllBySlot() {
    }

    @Test
    void findByDate() {
    }

    @Test
    void updateDiary() {
    }

    @Test
    void deleteBySlot() {
    }
}