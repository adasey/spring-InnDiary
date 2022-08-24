package com.diary.inn.InnDiary.work.controller.api.json;

import com.diary.inn.InnDiary.work.domain.api.DiaryJson;
import com.diary.inn.InnDiary.work.domain.api.ToDoJson;
import com.diary.inn.InnDiary.work.domain.info.Member;
import com.diary.inn.InnDiary.work.service.api.DiaryJsonService;
import com.diary.inn.InnDiary.work.service.api.ToDoJsonService;
import com.diary.inn.InnDiary.work.service.info.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
* rest api controller
* it is request and response api
* */
@Slf4j // for test
@RequestMapping("/inn/apis")
@RequiredArgsConstructor
@RestController
public class JsonResponseController {
    private final MemberService memberService;
    private final DiaryJsonService diaryJsonService;
    private final ToDoJsonService toDoJsonService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/register/member")
    public Member memberRegister(@RequestParam("email") String loginId, @RequestParam("company") int company) {
        Member findMember = memberService.findByEmailNCompany(loginId, company);

        if (findMember != null) {
            return findMember;
        }
        else {
            return Member.builder().seq(null).loginId(null).state(0).company(0).build();
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/send/diary")
    public List<DiaryJson> diaryRequest(@RequestParam("email") String loginId, @RequestParam("company") int company) {
        Member findMember = memberService.findByEmailNCompany(loginId, company);

        if (findMember != null) {
            return diaryJsonService.userDataFindAll(findMember);
        }

        return null;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/send/todo")
    public List<ToDoJson> toDoRequest(@RequestParam("email") String loginId, @RequestParam("company") int company) throws JsonProcessingException {
        Member findMember = memberService.findByEmailNCompany(loginId, company);

        if (findMember != null) {
            return toDoJsonService.userDataFindAll(findMember);
        }

        return null;
    }

    @ResponseBody
    @PostMapping("/register/member")
    public Member memberRegister(@RequestBody Member member) {
        if (!memberService.existMember(member)) {
            memberService.join(member);
        }

        return memberService.findByEmailNCompany(member.getLoginId(), member.getCompany());
    }

    @ResponseBody
    @PostMapping("/send/diary")
    public DiaryJson diaryResponse(@RequestBody DiaryJson diaryJson) {
        Member findMember = memberService.findByEmailNCompany(diaryJson.getMemberId(), diaryJson.getMemberCompany());

        DiaryJson findDiary = diaryJsonService.userDataFindWithSave(findMember, diaryJson.getSaveTitle());

        if (findDiary != null) {
            if (!diaryJson.getDiary().equals(findDiary.getDiary())) {
                diaryJsonService.uploadToJson(findDiary.getSeq(), diaryJson.getDiary());
            }
        }
        else {
            diaryJson.setMemberSeq(findMember.getSeq());
            diaryJsonService.join(diaryJson);
        }

        return diaryJsonService.userDataFindWithSave(findMember, diaryJson.getSaveTitle());
    }

    @ResponseBody
    @PostMapping("/send/todo")
    public ToDoJson todoResponse(@RequestBody ToDoJson toDoJson) {
        Member findMember = memberService.findByEmailNCompany(toDoJson.getMemberId(), toDoJson.getMemberCompany());

        ToDoJson findToDo = toDoJsonService.userDataFindWithSave(findMember, toDoJson.getSaveTitle());

        if (findToDo != null) {
            if (!toDoJson.getTodo().equals(findToDo.getTodo())) {
                toDoJsonService.uploadToJson(findToDo.getSeq(), toDoJson.getTodo());
            }
        }
        else {
            toDoJson.setMemberSeq(findMember.getSeq());
            toDoJsonService.join(toDoJson);
        }

        return toDoJsonService.userDataFindWithSave(findMember, toDoJson.getSaveTitle());
    }
}
