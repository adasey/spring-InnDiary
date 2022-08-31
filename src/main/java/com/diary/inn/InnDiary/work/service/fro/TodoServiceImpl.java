package com.diary.inn.InnDiary.work.service.fro;

import com.diary.inn.InnDiary.work.domain.bef.TodoJson;
import com.diary.inn.InnDiary.work.domain.fro.Todo;
import com.diary.inn.InnDiary.work.domain.fro.TodoInfo;
import com.diary.inn.InnDiary.work.entity.bef.TodoJsonEntity;
import com.diary.inn.InnDiary.work.entity.fro.TodoEntity;
import com.diary.inn.InnDiary.work.repository.fro.TodoRepository;
import com.diary.inn.InnDiary.work.service.bef.TodoJsonConversionService;
import com.diary.inn.InnDiary.work.service.bef.TodoJsonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoServiceImpl implements TodoService, TodoConversionService {
    private final TodoRepository todoRepository;
    private final TodoJsonService todoJsonService;
    private final TodoJsonConversionService todoJsonConversionService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Todo entityToDto(TodoEntity entity, TodoJsonEntity jsonEntity) {
        return Todo.builder()
                .seq(entity.getSeq())
                .toDoId(entity.getTodoId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .todoDate(entity.getTodoDate())
                .toDoJsonSeq(jsonEntity.getSeq())
                .todoSlotNum(jsonEntity.getSlotNum())
                .todoSaveDate(jsonEntity.getModDate())
                .build();
    }

    @Override
    public TodoEntity dtoToEntity(Todo dto) {
        TodoJsonEntity toDoJsonEntity = TodoJsonEntity.builder()
                .seq(dto.getToDoJsonSeq())
                .build();

        return TodoEntity.builder()
                .seq(dto.getSeq())
                .todoId(dto.getToDoId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .todoDate(dto.getTodoDate())
                .todo(toDoJsonEntity)
                .build();
    }

    @Override
    public Todo toDoJoin(Todo diary) {
        TodoEntity save = todoRepository.save(dtoToEntity(diary));
        TodoJson toDoJson = todoJsonService.savedToDoFind(save.getTodoId());

        if (toDoJson != null) {
            TodoJsonEntity jsonEntity = todoJsonConversionService.dtoToEntity(toDoJson);

            return entityToDto(save, jsonEntity);
        }
        else {
            return null;
        }
    }

    @Override
    public List<Todo> toDoSaveAll(TodoJson todoJson) {
        List<TodoInfo> buildJson = new ArrayList<>();
        List<Todo> result = new ArrayList<>();

        try {
            if (todoJson.getTodoList().contains("[") && todoJson.getTodoList().contains("]")) {
                buildJson = objectMapper.readValue(todoJson.getTodoList(), new TypeReference<>() {});
            }
            else {
                TodoInfo toDoInfo = objectMapper.readValue(todoJson.getTodoList(), TodoInfo.class);
                buildJson.add(toDoInfo);
            }
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        log.info("error reason search : {}", buildJson);

        for (TodoInfo data : buildJson) {
            Todo toDo = Todo.builder()
                    .toDoId(data.getSeq())
                    .todoDate(LocalDateTime.parse(data.getDate()))
                    .content(data.getContent())
                    .title(data.getTitle())
                    .todoSaveDate(todoJson.getModDate())
                    .todoSlotNum(todoJson.getSlotNum())
                    .toDoJsonSeq(todoJson.getSeq())
                    .build();

            result.add(toDo);

            todoRepository.save(dtoToEntity(toDo));
        }

        return result;
    }

    @Override
    public void toDoDelete(List<TodoJson> todoJsons) {

    }
}
