package com.diary.inn.InnDiary.work.service.data;

import com.diary.inn.InnDiary.work.domain.Slot;
import com.diary.inn.InnDiary.work.domain.Todo;
import com.diary.inn.InnDiary.work.entity.SlotEntity;
import com.diary.inn.InnDiary.work.entity.TodoEntity;
import com.diary.inn.InnDiary.work.repository.SearchTodoRepository;
import com.diary.inn.InnDiary.work.repository.TodoRepository;
import com.diary.inn.InnDiary.work.service.CommonPerform;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoServiceImpl extends CommonPerform implements TodoService, TodoConvertService {
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
    public void setWantSlot(Slot slot) {
        searchTodoRepository.setSlot(slotToEntity(slot));
    }

    @Override
    public Slot getNowSlot() {
        return slotEntityToDto(searchTodoRepository.getSlot());
    }

    @Override
    public boolean isSlotSetting() {
        return searchTodoRepository.isSlotSetting();
    }

    @Override
    public Todo createTodo(Todo todo) {
        return entityToDto(todoRepository.save(dtoToEntity(todo)));
    }

    @Override
    public Todo findTodoBySeq(Long todoSeq) {
        if (searchTodoRepository.isSlotSetting()) {
            return entityToDto(searchTodoRepository.findBySeq(todoSeq));
        }
        log.info("please setting slot value before find");
        return null;
    }

    @Override
    public List<Todo> findAllTodoBySlot() {
        if (searchTodoRepository.isSlotSetting()) {
            List<Todo> found = new ArrayList<>();
            List<TodoEntity> fTodo = searchTodoRepository.findAllBySlot();

            for (TodoEntity te : fTodo) {
                found.add(entityToDto(te));
            }
            return found;
        }
        log.info("please setting slot value before find");
        return null;
    }

    @Override
    public List<Todo> findMonthTodo(LocalDate date) {
        if (searchTodoRepository.isSlotSetting()) {
            List<Todo> result = new ArrayList<>();
            List<TodoEntity> todoByDate = searchTodoRepository.findByMonthDate(date);

            for (TodoEntity te : todoByDate) {
                result.add(entityToDto(te));
            }
            return result;
        }

        log.info("please setting slot value before find");
        return null;
    }

    @Override
    public List<Todo> findSixMonthTodo(LocalDate date) {
        if (searchTodoRepository.isSlotSetting()) {
            List<Todo> rTodo = new ArrayList<>();

            for (int i = -2; i <= 3; i++) {
                LocalDate tDate = null;
                if (i <= 0) {
                    tDate = date.minusMonths(-i);
                } else {
                    tDate = date.plusMonths(i);
                }

                List<TodoEntity> findMonthDate = searchTodoRepository.findByMonthDate(tDate);

                for (TodoEntity te : findMonthDate) {
                    rTodo.add(entityToDto(te));
                }
            }

            return rTodo;
        }

        log.info("please setting slot value before find");
        return null;
    }

    @Override
    public List<Todo> findBetweenMonthTodo(LocalDate startDate, LocalDate endDate) {
        if (searchTodoRepository.isSlotSetting()) {
            List<LocalDate> dates = splitDate(startDate, endDate);
            List<Todo> result = new ArrayList<>();

            for (int i = 0; i < dates.size(); i += 2) {
                List<TodoEntity> betweenMonthDiary = searchTodoRepository.findByBetweenMonthDate(dates.get(i), dates.get(i + 1));

                for (TodoEntity te : betweenMonthDiary) {
                    result.add(entityToDto(te));
                }

                result.clear();
            }

            return result;
        }

        log.info("please setting slot value before find");
        return null;
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
