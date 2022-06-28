package com.diary.inn.InnDiary.member;

import com.diary.inn.InnDiary.user.domain.Member;
import com.diary.inn.InnDiary.user.entity.MemberEntity;
import com.diary.inn.InnDiary.user.repository.MemberRepository;
import com.diary.inn.InnDiary.user.service.MemberConvertService;
import com.diary.inn.InnDiary.user.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberConvertService memberConvertService;
    @Autowired
    MemberRepository memberRepository;

    public Member makeAdminUser() {
        return Member.builder().loginId("admin@test").company(0).state(1).build();
    }

    public Member makeUser(int id) {
        return Member.builder().loginId("id" + id + "@test").company(0).state(0).build();
    }

    @Test
    void dtoNEntityConverterTest() {
        //given
        Member m = Member.builder().seq(0L).loginId("0").company(0).state(0).build();
        MemberEntity me = MemberEntity.builder().seq(0L).loginId("0").company(0).state(0).build();

        //when
        Member r = memberConvertService.entityToDto(me);
        MemberEntity re = memberConvertService.dtoToEntity(m);

        //then
        assertThat(m.getClass()).isEqualTo(r.getClass());
        assertThat(me.getClass()).isEqualTo(re.getClass());
    }

    @Test
    void joinTest() {
        //given
        Member member = Member.builder().loginId("test@test").company(0).state(0).build();

        //when
        Long result = memberService.join(member);
        Long find = memberRepository.findByLoginId(member.getLoginId()).getSeq();

        Long exist = memberService.join(member);

        //then
//        log.info("result : {}", result);
        log.info("result : {}, find : {}", result, find);
        assertThat(result).isEqualTo(find);
        assertThat(exist).isEqualTo(0L);
    }

    @Test
    void allMemberFound() {
        // given, when
        IntStream.rangeClosed(1, 20).forEach(i -> {
            memberService.join(makeUser(i));
        });

        // then
        assertThat(memberService.memberAll().size()).isEqualTo(20);
    }
}
