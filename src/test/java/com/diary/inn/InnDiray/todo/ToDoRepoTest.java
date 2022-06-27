package com.diary.inn.InnDiray.todo;

import com.diary.inn.InnDiray.api.repository.ToDoJsonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Slf4j
public class ToDoRepoTest {
    @Autowired
    ToDoJsonRepository toDoJsonRepository;

    public String test = "{'title' : 'test'}";
}
