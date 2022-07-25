package com.diary.inn.InnDiary.work.service.api;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public interface DiaryJsonProcess {
    List<String> stringArrayToJson(List<JsonNode> diary);
    List<JsonNode> jsonArrayToString(List<String> diary);
}
