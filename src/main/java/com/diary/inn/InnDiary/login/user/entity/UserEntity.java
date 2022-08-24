package com.diary.inn.InnDiary.login.user.entity;

import com.diary.inn.InnDiary.work.entity.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class UserEntity {

    private Long id;
    private String name;
    private String email;

    // firebase timestamp type
    private Date new_date;
    private Date update_date;
}
