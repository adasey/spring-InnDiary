package com.diary.inn.InnDiary.work.controller.info;

import com.diary.inn.InnDiary.login.domain.SessionUser;
import com.diary.inn.InnDiary.login.session.LoginSession;
import com.diary.inn.InnDiary.work.domain.Todo;
import com.diary.inn.InnDiary.work.service.data.TodoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/todos")
@Slf4j
public class TodoController {
    private final TodoService todoService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @GetMapping("/calendars")
    private String getCalenderTodo(@LoginSession SessionUser sUser, HttpSession session, Model model) {
        model.addAttribute("sUser", sUser);

        return "todo/todoCalendar";
    }

    @GetMapping("/calendars/values/now")
    @ResponseBody
    private JsonNode callTodoSearch(@RequestParam String start, @RequestParam String end) throws JsonProcessingException {
        LocalDate startDate = stringToLocalDate(start);
        LocalDate endDate = stringToLocalDate(end);
        List<Todo> monthTodo = todoService.findBetweenMonthTodo(startDate, endDate);

        return objectMapper.readTree(todoToTodoJson(monthTodo).toString());
    }

    private LocalDate stringToLocalDate(String time) {
        String[] ts = time.split("T");
        LocalDate ldt = null;

        if (ts[1].equals("00:00:00+09:00")) {
            ldt = LocalDate.parse(ts[0], DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        else {
            String parsedTime = ts[0] + "T" + parseStringTimeToLocalDate(ts[1]);
            ldt = LocalDate.parse(parsedTime, DateTimeFormatter.ofPattern("yyyy-MM-ddTHHmm"));
        }

        return ldt;
    }

    private String parseStringTimeToLocalDate(String time) {
        String[] onlyTime = time.split("\\+");
        String[] hourMinSec = onlyTime[0].split(":");

        return hourMinSec[0] + hourMinSec[1];
    }

    private List<String> todoToTodoJson(List<Todo> todos) throws JsonProcessingException {
        List<String> result = new ArrayList<>();
        Map<String, Object> tMap = new HashMap<>();

        for (Todo t : todos) {
            log.info("test value call well : {}", t);
            tMap.put("id", t.getTodoSeq().toString());
            tMap.put("title", t.getTitle());
            tMap.put("start", t.getDate().toString());

            result.add(objectMapper.writeValueAsString(tMap));
            tMap.clear();
        }
        log.info("test value stack well : {}", result);
        return result;
    }

    @GetMapping("/lists")
    private String getListTodo(@LoginSession SessionUser sUser, Model model) {
        model.addAttribute("sUser", sUser);

        List<Todo> nowTodo = todoService.findMonthTodo(LocalDate.now());
        model.addAttribute("todos", nowTodo);
        log.info("now todo list value : {}", nowTodo);

        return "todo/todoList";
    }
}
