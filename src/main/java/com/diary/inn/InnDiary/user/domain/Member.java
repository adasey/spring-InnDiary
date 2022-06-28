package com.diary.inn.InnDiary.user.domain;

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
