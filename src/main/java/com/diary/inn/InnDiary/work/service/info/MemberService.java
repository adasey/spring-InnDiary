package com.diary.inn.InnDiary.work.service.info;

import com.diary.inn.InnDiary.work.domain.info.Member;

import java.util.List;

public interface MemberService {
    Long join(Member member);
    Boolean existMember(Member member);
    List<Member> memberAll();
}
