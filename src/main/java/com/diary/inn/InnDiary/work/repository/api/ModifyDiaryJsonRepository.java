package com.diary.inn.InnDiary.work.repository.api;

import com.diary.inn.InnDiary.work.entity.api.DiaryJsonEntity;

import java.util.List;

public interface ModifyDiaryJsonRepository {
    List<DiaryJsonEntity> findAllByEmail(String email);
    DiaryJsonEntity findByEmailNSave(String email, String save);
    void saveJsonByEmailNSave(DiaryJsonEntity entity);
}
