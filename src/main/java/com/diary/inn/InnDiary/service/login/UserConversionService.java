package com.diary.inn.InnDiary.service.login;

import com.diary.inn.InnDiary.domain.login.User;
import com.diary.inn.InnDiary.entity.UserEntity;

public interface UserConversionService {
    UserEntity dtoToEntity(User u);
    User entityToDomain(UserEntity ue);
}
