package com.diary.inn.InnDiary.work.service.info;

import com.diary.inn.InnDiary.work.domain.api.ToDoJson;
import com.diary.inn.InnDiary.work.domain.info.ToDo;

import java.util.List;

public interface ToDoService {
    ToDo toDoJoin(ToDo diary);
    List<ToDo> toDoSaveAll(ToDoJson diaryJson);
    void toDoDelete(List<ToDoJson> toDoJsons);
}
