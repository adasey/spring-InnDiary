package com.diary.inn.InnDiray.diary;

import com.diary.inn.InnDiray.api.repository.DiaryJsonRepository;
import org.junit.jupiter.api.Test;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Slf4j
public class DiaryRepoTest {
    @Autowired
    DiaryJsonRepository diaryJsonRepository;

    public String test = "{'title' : 'test'}";

    @Test
    void diaryFindTest() {

    }
}
