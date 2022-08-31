package com.diary.inn.InnDiary.login.service;

import com.diary.inn.InnDiary.login.domain.User;

public interface UserService {
    void userLoginFail(String email);
    User findUserByEmail(String email);
}
