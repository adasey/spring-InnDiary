package com.diary.inn.InnDiary.service.login;

import com.diary.inn.InnDiary.domain.login.User;

public interface UserService {
    void userLoginFail(String email);
    User findUserByEmail(String email);
}
