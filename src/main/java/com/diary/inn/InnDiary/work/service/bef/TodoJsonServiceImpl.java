package com.diary.inn.InnDiary.work.service.bef;

import com.diary.inn.InnDiary.login.domain.User;
import com.diary.inn.InnDiary.login.entity.UserEntity;
import com.diary.inn.InnDiary.work.domain.bef.TodoJson;
import com.diary.inn.InnDiary.work.entity.bef.TodoJsonEntity;
import com.diary.inn.InnDiary.work.repository.bef.ModifyTodoJsonRepository;
import com.diary.inn.InnDiary.work.repository.bef.TodoJsonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TodoJsonServiceImpl implements TodoJsonConversionService, TodoJsonService {
    private final TodoJsonRepository todoJsonRepository;
    private final ModifyTodoJsonRepository modifyTodoJsonRepository;
//    private final MemberService memberService;
//    private final MemberConversionService memberConversionService;

    @Override
    public TodoJson entityToDto(TodoJsonEntity toDoJsonEntity) {
//        return ToDoJson.builder()
//                .seq(toDoJsonEntity.getSeq())
//                .saveTitle(toDoJsonEntity.getSaveTitle())
//                .todo(toDoJsonEntity.getTodo())
//                .modDate(toDoJsonEntity.getModDate())
//                .build();
        return null;
    }

    @Override
    public TodoJson entityToDto(TodoJsonEntity todoJsonEntity, UserEntity userEntity) {
//        return ToDoJson.builder()
//                .seq(toDoJsonEntity.getSeq())
//                .memberSeq(memberEntity.getSeq())
//                .memberState(memberEntity.getState())
//                .memberCompany(memberEntity.getCompany())
//                .memberId(memberEntity.getLoginId())
//                .saveTitle(toDoJsonEntity.getSaveTitle())
//                .todo(toDoJsonEntity.getTodo())
//                .modDate(toDoJsonEntity.getModDate())
//                .build();
        return null;
    }

    @Override
    public TodoJsonEntity dtoToEntity(TodoJson todoJson) {
//        MemberEntity memberEntity = MemberEntity.builder()
//                .seq(toDoJson.getMemberSeq())
//                .build();
//
//        return ToDoJsonEntity.builder()
//                .seq(toDoJson.getSeq())
//                .member(memberEntity)
//                .saveTitle(toDoJson.getSaveTitle())
//                .todo(toDoJson.getTodo())
//                .build();
        return null;
    }

    @Override
    public Long join(TodoJson todoJson) {
        TodoJsonEntity result = todoJsonRepository.save(dtoToEntity(todoJson));
        return result.getSeq();
    }

    @Override
    public void uploadToJson(Long seq, String json) {
//        modifyToDoJsonRepository.updateJson(seq, json);
    }

    @Override
    public void uploadToSaveTitle(Long seq, String saveTitle) {
//        modifyToDoJsonRepository.updateSaveTitle(seq, saveTitle);
    }

    @Override
    public void deleteWithEmailNSave(User user, String saveTitle) {
//        ToDoJsonEntity find = modifyToDoJsonRepository.findByEmailNCompanyWithSave(memberConversionService.dtoToEntity(member), saveTitle);
//
//        toDoJsonRepository.delete(find);
    }

    @Override
    public TodoJson savedToDoFind(Long seq) {
//        Optional<ToDoJsonEntity> found = toDoJsonRepository.findById(seq);
//
//        if (found.isPresent()) {
//            Member member = memberService.findByEmailNCompany(found.get().getMember().getLoginId(), found.get().getMember().getCompany());
//
//            return entityToDto(found.get(), memberConversionService.dtoToEntity(member));
//        }
//
//        return null;
        return null;
    }

    @Override
    public TodoJson userDataFindWithSave(User dto, String saveTitle) {
//        ToDoJsonEntity result = modifyToDoJsonRepository.findByEmailNCompanyWithSave(memberConversionService.dtoToEntity(dto), saveTitle);
//        if (result != null) {
//            return entityToDto(result, memberConversionService.dtoToEntity(dto));
//        }
//        else {
//            return null;
//        }
        return null;
    }

    @Override
    public List<TodoJson> userDataFindAll(User dto) {
//        MemberEntity memberEntity = memberConversionService.dtoToEntity(dto);
//
//        List<ToDoJsonEntity> found = modifyToDoJsonRepository.findAllByEmailNCompany(memberEntity);
//        List<ToDoJson> result = new ArrayList<>();
//
//        for (ToDoJsonEntity entity : found) {
//            result.add(entityToDto(entity, memberEntity));
//        }
//
//        if (result.size() > 0) {
//            return result;
//        }
//        else {
//            return null;
//        }
        return null;
    }
}
