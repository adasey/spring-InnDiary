package com.diary.inn.InnDiary.work.service.fro;

import com.diary.inn.InnDiary.work.domain.bef.TodoJson;
import com.diary.inn.InnDiary.work.domain.fro.Todo;

import java.util.List;

public interface TodoService {
    Todo toDoJoin(Todo diary);
    List<Todo> toDoSaveAll(TodoJson diaryJson);
    void toDoDelete(List<TodoJson> todoJsons);
}
