package com.diary.inn.InnDiary.work.service.info;

import com.diary.inn.InnDiary.work.domain.api.DiaryJson;
import com.diary.inn.InnDiary.work.domain.api.ToDoJson;
import com.diary.inn.InnDiary.work.domain.info.Diary;
import com.diary.inn.InnDiary.work.domain.info.DiaryInfo;
import com.diary.inn.InnDiary.work.domain.info.ToDo;
import com.diary.inn.InnDiary.work.domain.info.ToDoInfo;
import com.diary.inn.InnDiary.work.entity.api.DiaryJsonEntity;
import com.diary.inn.InnDiary.work.entity.api.ToDoJsonEntity;
import com.diary.inn.InnDiary.work.entity.info.ToDoEntity;
import com.diary.inn.InnDiary.work.repository.info.ToDoRepository;
import com.diary.inn.InnDiary.work.service.api.ToDoJsonConversionService;
import com.diary.inn.InnDiary.work.service.api.ToDoJsonService;
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
public class ToDoServiceImpl implements ToDoService, ToDoConversionService {
    private final ToDoRepository toDoRepository;
    private final ToDoJsonService toDoJsonService;
    private final ToDoJsonConversionService toDoJsonConversionService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ToDo entityToDto(ToDoEntity entity, ToDoJsonEntity jsonEntity) {
        return ToDo.builder()
                .seq(entity.getSeq())
                .toDoId(entity.getTodoId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .toDoDate(entity.getTodoDate())
                .toDoJsonSeq(jsonEntity.getSeq())
                .toDoSaveTitle(jsonEntity.getSaveTitle())
                .toDoSaveDate(jsonEntity.getModDate())
                .build();
    }

    @Override
    public ToDoEntity dtoToEntity(ToDo dto) {
        ToDoJsonEntity toDoJsonEntity = ToDoJsonEntity.builder()
                .seq(dto.getToDoJsonSeq())
                .build();

        return ToDoEntity.builder()
                .seq(dto.getSeq())
                .todoId(dto.getToDoId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .todoDate(dto.getToDoDate())
                .todo(toDoJsonEntity)
                .build();
    }

    @Override
    public ToDo toDoJoin(ToDo diary) {
        ToDoEntity save = toDoRepository.save(dtoToEntity(diary));
        ToDoJson toDoJson = toDoJsonService.savedToDoFind(save.getTodoId());

        if (toDoJson != null) {
            ToDoJsonEntity jsonEntity = toDoJsonConversionService.dtoToEntity(toDoJson);

            return entityToDto(save, jsonEntity);
        }
        else {
            return null;
        }
    }

    @Override
    public List<ToDo> toDoSaveAll(ToDoJson toDoJson) {
        List<ToDoInfo> buildJson = new ArrayList<>();
        List<ToDo> result = new ArrayList<>();

        try {
            if (toDoJson.getTodo().contains("[") && toDoJson.getTodo().contains("]")) {
                buildJson = objectMapper.readValue(toDoJson.getTodo(), new TypeReference<>() {});
            }
            else {
                ToDoInfo toDoInfo = objectMapper.readValue(toDoJson.getTodo(), ToDoInfo.class);
                buildJson.add(toDoInfo);
            }
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        log.info("error reason search : {}", buildJson);

        for (ToDoInfo data : buildJson) {
            ToDo toDo = ToDo.builder()
                    .toDoId(data.getSeq())
                    .toDoDate(LocalDateTime.parse(data.getDate()))
                    .content(data.getContent())
                    .title(data.getTitle())
                    .toDoSaveDate(toDoJson.getModDate())
                    .toDoSaveTitle(toDoJson.getSaveTitle())
                    .toDoJsonSeq(toDoJson.getSeq())
                    .build();

            result.add(toDo);

            toDoRepository.save(dtoToEntity(toDo));
        }

        return result;
    }

    @Override
    public void toDoDelete(List<ToDoJson> toDoJsons) {

    }
}
