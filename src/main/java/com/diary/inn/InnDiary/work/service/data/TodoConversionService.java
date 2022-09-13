package com.diary.inn.InnDiary.work.service.data;

import com.diary.inn.InnDiary.work.domain.Slot;
import com.diary.inn.InnDiary.work.domain.Todo;
import com.diary.inn.InnDiary.work.entity.SlotEntity;
import com.diary.inn.InnDiary.work.entity.TodoEntity;

public interface TodoConversionService {
    Todo entityToDto(TodoEntity te);
    TodoEntity dtoToEntity(Todo t);

    SlotEntity slotToEntity(Slot s);
}
