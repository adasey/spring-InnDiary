package com.diary.inn.InnDiary.work.service.info;

import com.diary.inn.InnDiary.work.domain.api.DiaryJson;
import com.diary.inn.InnDiary.work.domain.info.Diary;

import java.util.List;

public interface DiaryService {
    Diary diaryJoin(Diary diary);
    List<Diary> diarySaveAll(DiaryJson diaryJson);
    List<Diary> findDiaryByDiaryJson(DiaryJson diaryJson);
    void diaryDelete(List<DiaryJson> diaryJsons);
}
