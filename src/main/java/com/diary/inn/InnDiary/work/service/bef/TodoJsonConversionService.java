package com.diary.inn.InnDiary.work.service.bef;

import com.diary.inn.InnDiary.login.entity.UserEntity;
import com.diary.inn.InnDiary.work.domain.bef.TodoJson;
import com.diary.inn.InnDiary.work.entity.bef.TodoJsonEntity;

public interface TodoJsonConversionService {
    TodoJson entityToDto(TodoJsonEntity todoJsonEntity);
    TodoJson entityToDto(TodoJsonEntity todoJsonEntity, UserEntity userEntity);
    TodoJsonEntity dtoToEntity(TodoJson todoJson);
}
