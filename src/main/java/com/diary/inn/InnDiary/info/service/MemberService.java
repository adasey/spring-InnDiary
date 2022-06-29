package com.diary.inn.InnDiary.info.service;

import com.diary.inn.InnDiary.info.domain.Member;

import java.util.List;

public interface MemberService {
    Long join(Member member);
    Boolean existMember(Member member);
    List<Member> memberAll();
}
