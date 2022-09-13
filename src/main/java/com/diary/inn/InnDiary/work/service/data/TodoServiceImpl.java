package com.diary.inn.InnDiary.work.service.data;

import com.diary.inn.InnDiary.login.entity.UserEntity;
import com.diary.inn.InnDiary.work.domain.Slot;
import com.diary.inn.InnDiary.work.domain.Todo;
import com.diary.inn.InnDiary.work.entity.SlotEntity;
import com.diary.inn.InnDiary.work.entity.TodoEntity;
import com.diary.inn.InnDiary.work.repository.SearchTodoRepository;
import com.diary.inn.InnDiary.work.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoServiceImpl implements TodoService, TodoConversionService {
    private final TodoRepository todoRepository;
    private final SearchTodoRepository searchTodoRepository;

    @Override
    public Todo entityToDto(TodoEntity te) {
        return Todo.builder()
                .tSeq(te.getTSeq())
                .todoSeq(te.getTodoSeq())
                .title(te.getTitle())
                .date(te.getDate())
                .content(te.getContent())
                .slotSeq(te.getSlot().getSeq())
                .build();
    }

    @Override
    public TodoEntity dtoToEntity(Todo t) {
        SlotEntity se = SlotEntity.builder()
                .seq(t.getSlotSeq())
                .build();

        return TodoEntity.builder()
                .tSeq(t.getTSeq())
                .todoSeq(t.getTodoSeq())
                .title(t.getTitle())
                .date(t.getDate())
                .content(t.getContent())
                .slot(se)
                .build();
    }

    @Override
    public SlotEntity slotToEntity(Slot s) {
        UserEntity u = new UserEntity(s.getSeq(), s.getUserName(), s.getUserEmail(), s.getUserUid());

        return SlotEntity.builder()
                .seq(s.getSeq())
                .user(u)
                .which(s.getWhich())
                .slotNum(s.getSlotNum())
                .title(s.getTitle())
                .modDate(s.getModDate())
                .build();
    }

    @Override
    public Todo createTodo(Todo todo) {
        return entityToDto(todoRepository.save(dtoToEntity(todo)));
    }

    @Override
    public Todo findTodoBySlotNSeq(Slot slot, Long todoSeq) {
        return entityToDto(searchTodoRepository.findBySlotNSeq(slotToEntity(slot), todoSeq));
    }

    @Override
    public List<Todo> findAllTodoBySlot(Slot slot) {
        List<Todo> found = new ArrayList<>();
        List<TodoEntity> fTodo = searchTodoRepository.findAllBySlot(slotToEntity(slot));

        for (TodoEntity te : fTodo) {
            found.add(entityToDto(te));
        }

        return found;
    }

    @Override
    public void updateTodo(Todo todo) {
        searchTodoRepository.updateTodo(dtoToEntity(todo));
    }

    @Override
    public void deleteTodo(Todo todo) {
        todoRepository.delete(dtoToEntity(todo));
    }

    @Override
    public void deleteTodoBySlot(Long slotSeq) {
        searchTodoRepository.deleteBySlot(slotSeq);
    }
}
