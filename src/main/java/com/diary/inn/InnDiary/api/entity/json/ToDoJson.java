package com.diary.inn.InnDiary.api.entity.json;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class ToDoJson {
    private Long seq;
    private String loginId;
    private JsonNode Json;
    private LocalDateTime modDate;
}
