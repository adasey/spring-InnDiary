package com.diary.inn.InnDiary.work.service.api;

import com.diary.inn.InnDiary.work.domain.api.DiaryJson;

import java.util.List;

public interface DiaryJsonService{
    Long join(DiaryJson diaryJson);
    void uploadTo(DiaryJson diaryJson);
    List<DiaryJson> userDataFindAll(String email);
}
