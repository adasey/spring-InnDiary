package com.diary.inn.InnDiary.work.repository.info;

import com.diary.inn.InnDiary.work.domain.info.Diary;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class DiaryRepositoryImpl implements DiaryRepository {
    private static Map<Long, Diary> store = new HashMap<>(); // only work when server
}
