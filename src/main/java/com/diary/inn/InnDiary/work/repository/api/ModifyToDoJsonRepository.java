package com.diary.inn.InnDiary.work.repository.api;

import com.diary.inn.InnDiary.work.entity.api.ToDoJsonEntity;
import com.diary.inn.InnDiary.work.entity.info.MemberEntity;

import java.util.List;

public interface ModifyToDoJsonRepository {
    List<ToDoJsonEntity> findAllByEmailNCompany(MemberEntity mEntity);
    ToDoJsonEntity findByEmailNCompanyWithSave(MemberEntity mEntity, String saveTitle);
    void updateJson(Long seq, String json);
    void updateSaveTitle(Long seq, String saveTitle);
}
