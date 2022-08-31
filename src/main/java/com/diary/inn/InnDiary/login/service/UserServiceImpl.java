package com.diary.inn.InnDiary.login.service;

import com.diary.inn.InnDiary.login.domain.User;
import com.diary.inn.InnDiary.login.entity.UserEntity;
import com.diary.inn.InnDiary.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserConversionService {
    private final UserRepository userRepository;

    @Override
    public void userLoginFail(String email) {
        Optional<UserEntity> exiUser = userRepository.findByEmail(email);
        exiUser.ifPresent(userRepository::delete);
    }

    @Override
    public User findUserByEmail(String email) {
        Optional<UserEntity> found = userRepository.findByEmail(email);

        if (found.isPresent()) {
            return entityToDomain(found.get());
        } else {
            return User.builder().build();
        }
    }

    @Override
    public UserEntity dtoToEntity(User u) {
        UserEntity ue = UserEntity.builder()
                .email(u.getEmail())
                .name(u.getName())
                .company(u.getCompany())
                .role(u.getRole())
                .uid(u.getUid())
                .build();
        ue.setId(u.getId());
        return ue;
    }

    @Override
    public User entityToDomain(UserEntity ue) {
        return User.builder()
                .id(ue.getId())
                .email(ue.getEmail())
                .name(ue.getName())
                .company(ue.getCompany())
                .role(ue.getRole())
                .Uid(ue.getUid())
                .build();
    }
}
