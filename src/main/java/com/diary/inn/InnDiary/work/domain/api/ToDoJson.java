package com.diary.inn.InnDiary.work.domain.api;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ToDoJson {
    private Long seq;
    private String saveTitle;
    private String todo;

    private Long memberSeq;
    private String memberId;
    private int memberState;
    private int memberCompany;

    private LocalDateTime modDate;
}
