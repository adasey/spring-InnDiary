package com.diary.inn.InnDiary.user.service;

import com.diary.inn.InnDiary.user.domain.Member;

import java.util.List;

public interface MemberService {
    Long join(Member member);
    Boolean existMember(Member member);
    List<Member> memberAll();
}
