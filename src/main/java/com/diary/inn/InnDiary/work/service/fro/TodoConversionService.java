package com.diary.inn.InnDiary.work.service.fro;

import com.diary.inn.InnDiary.work.domain.fro.Todo;
import com.diary.inn.InnDiary.work.entity.bef.TodoJsonEntity;
import com.diary.inn.InnDiary.work.entity.fro.TodoEntity;

public interface TodoConversionService {
    Todo entityToDto(TodoEntity entity, TodoJsonEntity jsonEntity);
    TodoEntity dtoToEntity(Todo dto);
}
