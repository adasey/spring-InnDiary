package com.diary.inn.InnDiary.work.service.fro;

import com.diary.inn.InnDiary.work.domain.bef.DiaryJson;
import com.diary.inn.InnDiary.work.domain.fro.Diary;

import java.util.List;

public interface DiaryService {
    Diary diaryJoin(Diary diary);
    List<Diary> diarySaveAll(DiaryJson diaryJson);
    List<Diary> findDiaryByDiaryJson(DiaryJson diaryJson);
    void diaryDelete(List<DiaryJson> diaryJsons);
}
