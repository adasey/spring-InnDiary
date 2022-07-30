package com.diary.inn.InnDiary.work.service.info;

import com.diary.inn.InnDiary.work.domain.info.Member;
import com.diary.inn.InnDiary.work.entity.info.MemberEntity;

import java.util.List;

public interface MemberService {
    Long join(Member member);
    Boolean existMember(Member member);
    Member findByEmail(String loginId);
    Member findByEmailNCompany(String loginId, int company);
    List<Member> memberAll();
    void deleteByEmail(String email);
    void updateMemberState(String email, int state);
}
