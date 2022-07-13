package com.diary.inn.InnDiary.work.domain.info;

import lombok.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class Member {
    private Long seq;
    private String loginId;
    private int state;
    private int company;
}