package com.diary.inn.InnDiary.member;

import com.diary.inn.InnDiary.work.domain.info.Member;
import com.diary.inn.InnDiary.work.entity.info.MemberEntity;
import com.diary.inn.InnDiary.work.repository.info.MemberRepository;
import com.diary.inn.InnDiary.work.service.info.MemberConversionService;
import com.diary.inn.InnDiary.work.service.info.MemberService;
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
    MemberConversionService memberConversionService;
    @Autowired
    MemberRepository memberRepository;

    public Member makeAdminUser() {
        return Member.builder().loginId("lmo9903@gmail.com").company(0).state(1).build();
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
        Member r = memberConversionService.entityToDto(me);
        MemberEntity re = memberConversionService.dtoToEntity(m);

        //then
        assertThat(m.getClass()).isEqualTo(r.getClass());
        assertThat(me.getClass()).isEqualTo(re.getClass());
    }

    @Test
    void joinTest() {
        //given
        Member member = Member.builder().loginId("test@test.com").company(1).state(1).build();
//        Member member1 = Member.builder().loginId("lmo9903@naver.com").company(1).state(1).build();
//        Member member2 = Member.builder().loginId("lmo9903@gmail.com").company(0).state(0).build();

        //when
        Long result = memberService.join(member);
        Long find = memberRepository.findByLoginId(member.getLoginId()).getSeq();

        //then
//        log.info("result : {}", result);
        log.info("result : {}, find : {}", result, find);
        assertThat(result).isEqualTo(find);
    }

    @Test
    void findWithEmailAndCompanyTest() {
        //given
        Member member = Member.builder().loginId("test@test.com").company(1).state(1).build();
        Long take = memberService.join(member);

        //when
        MemberEntity result = memberRepository.findByLoginIdNCompany(member.getLoginId(), member.getCompany());

        //then
//        log.info("result : {}", result);
        log.info("result : {}", result);
        assertThat(member.getLoginId()).isEqualTo(result.getLoginId());
    }

    @Test
    void allMemberFound() {
        // given, when
        IntStream.rangeClosed(1, 20).forEach(i -> {
            memberService.join(makeUser(i));
        });

        // then
        assertThat(memberService.memberAll().size()).isEqualTo(22);
    }

    @Test
    void memberUpdateServiceTest() {
        // given
        Member m = makeUser(1);

        memberService.join(m);

        // when
        memberService.updateMemberState(m.getLoginId(), 1);
        Member result = memberService.findByEmail(m.getLoginId());

        // then
        assertThat(m.getLoginId()).isEqualTo(result.getLoginId());
        assertThat(m.getState()).isNotEqualTo(result.getState());
    }

    @Test
    void memberDeleteTest() {
        // given
        Member m = makeUser(1);

        memberService.join(m);
        boolean test = memberService.existMember(m);

        // when
        memberService.deleteByEmail(m.getLoginId());
        boolean result = memberService.existMember(m);

        log.info("before delete data : {}", test);
        log.info("after delete data : {}", result);

        // then
        assertThat(result).isEqualTo(false);
    }
}
