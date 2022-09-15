package com.diary.inn.InnDiary.work.controller.info;

import com.diary.inn.InnDiary.work.service.data.DiaryConversionService;
import com.diary.inn.InnDiary.work.service.data.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/diaries")
public class DiaryController {
    private final DiaryService diaryService;
    private final DiaryConversionService diaryConversionService;
}
