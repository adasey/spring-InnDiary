package com.diary.inn.InnDiary.work.service.api;

import com.diary.inn.InnDiary.work.domain.api.DiaryJson;
import com.diary.inn.InnDiary.work.domain.api.ToDoJson;
import com.diary.inn.InnDiary.work.entity.api.DiaryJsonEntity;
import com.diary.inn.InnDiary.work.entity.api.ToDoJsonEntity;
import com.diary.inn.InnDiary.work.entity.info.MemberEntity;

public interface ToDoJsonConversionService {
    ToDoJson entityToDto(ToDoJsonEntity toDoJsonEntity);
    ToDoJson entityToDto(ToDoJsonEntity toDoJsonEntity, MemberEntity memberEntity);
    ToDoJsonEntity dtoToEntity(ToDoJson toDoJson);
}
