package com.diary.inn.InnDiary.login.user.service;

import com.diary.inn.InnDiary.login.user.entity.UserEntity;
import com.diary.inn.InnDiary.login.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void userLoginFail(String email) {
        Optional<UserEntity> exiUser = userRepository.findByEmail(email);
        exiUser.ifPresent(userRepository::delete);
    }
}
