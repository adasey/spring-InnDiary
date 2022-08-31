package com.diary.inn.InnDiary.work.repository.fro;

import com.diary.inn.InnDiary.work.entity.fro.DiaryEntity;

import java.util.List;

public interface SearchDiaryRepository {
    List<DiaryEntity> diaryByDiaryJson(Long seq);
    void deleteByDiaryJsonSeq(Long seq);
}
