package com.diary.inn.InnDiary.work.repository.api;

import com.diary.inn.InnDiary.work.entity.api.DiaryJsonEntity;
import com.diary.inn.InnDiary.work.entity.info.MemberEntity;

import java.util.List;

public interface ModifyDiaryJsonRepository {
    List<DiaryJsonEntity> findAllByEmailNCompany(MemberEntity mEntity);
    DiaryJsonEntity findByEmailNCompanyWithSave(MemberEntity mEntity, String saveTitle);
    void updateJson(Long seq, String json);
    void updateSaveTitle(Long seq, String saveTitle);
}
