package com.diary.inn.InnDiary.work.service.api;

import com.diary.inn.InnDiary.work.domain.api.DiaryJson;
import com.diary.inn.InnDiary.work.domain.info.Member;

import java.util.List;

public interface DiaryJsonService{
    Long join(DiaryJson diaryJson);
    void uploadToJson(Long seq, String json);
    void uploadToSaveTitle(Long seq, String saveTitle);
    void deleteWithEmailNSave(Member dto, String saveTitle);
    DiaryJson userDataFindWithSave(Member dto, String saveTitle);
    List<DiaryJson> userDataFindAll(Member dto);
}
