package com.diary.inn.InnDiary.work.controller.bef;

import com.diary.inn.InnDiary.work.domain.bef.DiaryJson;
import com.diary.inn.InnDiary.work.repository.bef.ModifyDiaryJsonRepository;
import com.diary.inn.InnDiary.work.service.bef.DiaryJsonService;
import com.diary.inn.InnDiary.work.service.bef.TodoJsonService;
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
    private final DiaryJsonService diaryJsonService;
    private final TodoJsonService toDoJsonService;

//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/get/diary")
//    public List<DiaryJson> diaryRequest(@RequestParam("email") String loginId, @RequestParam("company") int company) {
////        Member findMember = memberService.findByEmailNCompany(loginId, company);
////
////        if (findMember != null) {
////            return diaryJsonService.userDataFindAll(findMember);
////        }
////
//        return null;
//    }
//
//    @ResponseBody
//    @PostMapping("/send/diary")
//    public DiaryJson diaryResponse(@RequestBody DiaryJson diaryJson) {
////        Member findMember = memberService.findByEmailNCompany(diaryJson.getMemberId(), diaryJson.getMemberCompany());
////
////        DiaryJson findDiary = diaryJsonService.userDataFindWithSave(findMember, diaryJson.getSaveTitle());
////
////        if (findDiary != null) {
////            if (!diaryJson.getDiary().equals(findDiary.getDiary())) {
////                diaryJsonService.uploadToJson(findDiary.getSeq(), diaryJson.getDiary());
////            }
////        }
////        else {
////            diaryJson.setMemberSeq(findMember.getSeq());
////            diaryJsonService.join(diaryJson);
////        }
////
////        return diaryJsonService.userDataFindWithSave(findMember, diaryJson.getSaveTitle());
//        return null;
//    }
}
