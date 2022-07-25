package com.diary.inn.InnDiary.member;

import com.diary.inn.InnDiary.work.entity.info.MemberEntity;
import com.diary.inn.InnDiary.work.repository.info.MemberRepository;
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

    @Test
    void MemberUpdateStateTest() {
        // given
        MemberEntity entity = MemberEntity.builder()
                .loginId("test@test.com")
                .state(0)
                .company(0)
                .build();

        memberRepository.save(entity);
        MemberEntity find = memberRepository.findByLoginId("test@test.com");
        log.info("before update value : {}", find);

        // when
        memberRepository.updateMemberState(find.getSeq(), 1);
        MemberEntity result = memberRepository.findByLoginId("test@test.com");

        log.info("after update value : {}", result);

        // then
        assertThat(entity.getLoginId()).isEqualTo(result.getLoginId());
        assertThat(entity.getCompany()).isEqualTo(result.getCompany());
        assertThat(result.getState()).isEqualTo(1);
    }
}
