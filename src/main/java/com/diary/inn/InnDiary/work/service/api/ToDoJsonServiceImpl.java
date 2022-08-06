package com.diary.inn.InnDiary.work.service.api;

import com.diary.inn.InnDiary.work.domain.api.DiaryJson;
import com.diary.inn.InnDiary.work.domain.api.ToDoJson;
import com.diary.inn.InnDiary.work.domain.info.Member;
import com.diary.inn.InnDiary.work.entity.api.DiaryJsonEntity;
import com.diary.inn.InnDiary.work.entity.api.ToDoJsonEntity;
import com.diary.inn.InnDiary.work.entity.info.MemberEntity;
import com.diary.inn.InnDiary.work.repository.api.ModifyToDoJsonRepository;
import com.diary.inn.InnDiary.work.repository.api.ToDoJsonRepository;
import com.diary.inn.InnDiary.work.service.info.MemberConversionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ToDoJsonServiceImpl implements ToDoJsonConversionService, ToDoJsonService {
    private final ToDoJsonRepository toDoJsonRepository;
    private final ModifyToDoJsonRepository modifyToDoJsonRepository;
    private final MemberConversionService memberConversionService;

    @Override
    public ToDoJson entityToDto(ToDoJsonEntity toDoJsonEntity) {
        return ToDoJson.builder()
                .seq(toDoJsonEntity.getSeq())
                .saveTitle(toDoJsonEntity.getSaveTitle())
                .todo(toDoJsonEntity.getTodo())
                .modDate(toDoJsonEntity.getModDate())
                .build();
    }

    @Override
    public ToDoJson entityToDto(ToDoJsonEntity toDoJsonEntity, MemberEntity memberEntity) {
        return ToDoJson.builder()
                .seq(toDoJsonEntity.getSeq())
                .memberSeq(memberEntity.getSeq())
                .memberState(memberEntity.getState())
                .memberCompany(memberEntity.getCompany())
                .memberId(memberEntity.getLoginId())
                .saveTitle(toDoJsonEntity.getSaveTitle())
                .todo(toDoJsonEntity.getTodo())
                .modDate(toDoJsonEntity.getModDate())
                .build();
    }

    @Override
    public ToDoJsonEntity dtoToEntity(ToDoJson toDoJson) {
        MemberEntity memberEntity = MemberEntity.builder()
                .seq(toDoJson.getMemberSeq())
                .build();

        return ToDoJsonEntity.builder()
                .seq(toDoJson.getSeq())
                .member(memberEntity)
                .saveTitle(toDoJson.getSaveTitle())
                .todo(toDoJson.getTodo())
                .build();
    }

    @Override
    public Long join(ToDoJson toDoJson) {
        ToDoJsonEntity result = toDoJsonRepository.save(dtoToEntity(toDoJson));
        return result.getSeq();
    }

    @Override
    public void uploadToJson(Long seq, String json) {
        modifyToDoJsonRepository.updateJson(seq, json);
    }

    @Override
    public void uploadToSaveTitle(Long seq, String saveTitle) {
        modifyToDoJsonRepository.updateSaveTitle(seq, saveTitle);
    }

    @Override
    public void deleteWithEmailNSave(Member member, String saveTitle) {
        ToDoJsonEntity find = modifyToDoJsonRepository.findByEmailNCompanyWithSave(memberConversionService.dtoToEntity(member), saveTitle);

        toDoJsonRepository.delete(find);
    }

    @Override
    public ToDoJson userDataFindWithSave(Member dto, String saveTitle) {
        ToDoJsonEntity result = modifyToDoJsonRepository.findByEmailNCompanyWithSave(memberConversionService.dtoToEntity(dto), saveTitle);
        if (result != null) {
            return entityToDto(result, memberConversionService.dtoToEntity(dto));
        }
        else {
            return null;
        }
    }

    @Override
    public List<ToDoJson> userDataFindAll(Member dto) {
        MemberEntity memberEntity = memberConversionService.dtoToEntity(dto);

        List<ToDoJsonEntity> found = modifyToDoJsonRepository.findAllByEmailNCompany(memberEntity);
        List<ToDoJson> result = new ArrayList<>();

        for (ToDoJsonEntity entity : found) {
            result.add(entityToDto(entity, memberEntity));
        }

        if (result.size() > 0) {
            return result;
        }
        else {
            return null;
        }
    }
}
