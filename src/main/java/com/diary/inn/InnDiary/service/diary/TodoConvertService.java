package com.diary.inn.InnDiary.service.diary;

import com.diary.inn.InnDiary.domain.diary.Todo;
import com.diary.inn.InnDiary.entity.diary.TodoEntity;

public interface TodoConvertService {
    Todo entityToDto(TodoEntity te);
    TodoEntity dtoToEntity(Todo t);
}
