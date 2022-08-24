package com.diary.inn.InnDiary.work.repository.info;

import com.diary.inn.InnDiary.work.entity.info.DiaryEntity;

import java.util.List;

public interface SearchDiaryRepository {
    List<DiaryEntity> diaryByDiaryJson(Long seq);
    void deleteByDiaryJsonSeq(Long seq);
}
