package com.diary.inn.InnDiary.work.service.bef;

import com.diary.inn.InnDiary.login.domain.User;
import com.diary.inn.InnDiary.work.domain.bef.TodoJson;

import java.util.List;

public interface TodoJsonService {
    Long join(TodoJson todoJson);
    void uploadToJson(Long seq, String json);
    void uploadToSaveTitle(Long seq, String saveTitle);
    void deleteWithEmailNSave(User user, String saveTitle);
    TodoJson savedToDoFind(Long seq);
    TodoJson userDataFindWithSave(User dto, String saveTitle);
    List<TodoJson> userDataFindAll(User dto);
}
