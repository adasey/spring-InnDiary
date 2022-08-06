package com.diary.inn.InnDiary.work.service.api;

import com.diary.inn.InnDiary.work.domain.api.ToDoJson;
import com.diary.inn.InnDiary.work.domain.info.Member;

import java.util.List;

public interface ToDoJsonService {
    Long join(ToDoJson toDoJson);
    void uploadToJson(Long seq, String json);
    void uploadToSaveTitle(Long seq, String saveTitle);
    void deleteWithEmailNSave(Member member, String saveTitle);
    ToDoJson userDataFindWithSave(Member dto, String saveTitle);
    List<ToDoJson> userDataFindAll(Member dto);
}
