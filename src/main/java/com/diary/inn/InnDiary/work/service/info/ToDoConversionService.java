package com.diary.inn.InnDiary.work.service.info;

import com.diary.inn.InnDiary.work.domain.info.ToDo;
import com.diary.inn.InnDiary.work.entity.api.ToDoJsonEntity;
import com.diary.inn.InnDiary.work.entity.info.ToDoEntity;

public interface ToDoConversionService {
    ToDo entityToDto(ToDoEntity entity, ToDoJsonEntity jsonEntity);
    ToDoEntity dtoToEntity(ToDo dto);
}
