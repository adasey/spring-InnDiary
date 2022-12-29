package com.diary.inn.InnDiary.domain.login;

import com.diary.inn.InnDiary.utils.attr.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
public class User {
    private Long id;
    private String Uid;
    private String name;
    private String email;
    private int company;
    private Role role;
}
