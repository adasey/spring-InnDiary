package com.diary.inn.InnDiary.login.service;

import com.diary.inn.InnDiary.login.domain.User;
import com.diary.inn.InnDiary.login.entity.UserEntity;

public interface UserConverterService {
    UserEntity dtoToEntity(User u);
    User entityToDomain(UserEntity ue);
}
