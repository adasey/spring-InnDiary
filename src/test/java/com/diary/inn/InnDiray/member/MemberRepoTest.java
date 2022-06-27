package com.diary.inn.InnDiray.member;

import com.diary.inn.InnDiray.user.entity.MemberEntity;
import com.diary.inn.InnDiray.user.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
@SpringBootTest
public class MemberRepoTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void findMemberTestWithEmail() {
        // given
        MemberEntity entity = MemberEntity.builder()
                .loginId("test@test.com")
                .state(0)
                .company(0)
                .build();

        MemberEntity check = null;

        MemberEntity result = memberRepository.save(entity);
        // when
        check = memberRepository.findByLoginId(result.getLoginId());

        // then
        assertThat(check).isEqualTo(result);
    }

    @Test
    void saveMemberEntity() {
        // given
        MemberEntity entity = MemberEntity.builder()
                .loginId("test@test.com")
                .state(0)
                .company(0)
                .build();

        // when
        MemberEntity result = memberRepository.save(entity);

        // then
        assertThat(entity.getLoginId()).isEqualTo(result.getLoginId());
        assertThat(entity.getCompany()).isEqualTo(result.getCompany());
        assertThat(entity.getState()).isEqualTo(result.getState());
    }
}
