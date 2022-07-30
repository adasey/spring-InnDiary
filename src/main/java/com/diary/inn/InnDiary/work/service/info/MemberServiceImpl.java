package com.diary.inn.InnDiary.work.service.info;

import com.diary.inn.InnDiary.work.domain.info.Member;
import com.diary.inn.InnDiary.work.entity.info.MemberEntity;
import com.diary.inn.InnDiary.work.repository.info.MemberRepository;
import com.diary.inn.InnDiary.work.repository.info.SearchWithPageMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService, MemberConversionService {
    private final MemberRepository memberRepository;
    private final SearchWithPageMemberRepository searchWithPageMemberRepository;

    @Override
    public Member entityToDto(MemberEntity memberEntity) {
        return Member.builder()
                .seq(memberEntity.getSeq())
                .loginId(memberEntity.getLoginId())
                .company(memberEntity.getCompany())
                .state(memberEntity.getState())
                .build();
    }

    @Override
    public MemberEntity dtoToEntity(Member member) {
        return MemberEntity.builder()
                .seq(member.getSeq())
                .loginId(member.getLoginId())
                .company(member.getCompany())
                .state(member.getState())
                .build();
    }

    @Override
    public Long join(Member member) {
        if (existMember(member)) {
            return 0L;
        }

        return memberRepository.save(dtoToEntity(member)).getSeq();
    }

    @Override
    public Boolean existMember(Member member) {
        return memberRepository.findByLoginId(member.getLoginId()) != null;
    }

    @Override
    public Member findByEmail(String loginId) {
        return entityToDto(memberRepository.findByLoginId(loginId));
    }

    @Override
    public Member findByEmailNCompany(String loginId, int company) {
        return entityToDto(memberRepository.findByLoginIdNCompany(loginId, company));
    }

    @Override
    public List<Member> memberAll() {
        List<MemberEntity> search = memberRepository.findAll();
        List<Member> result = new ArrayList<>();

        for (MemberEntity entity : search) {
            result.add(entityToDto(entity));
        }

        return result;
    }

    @Override
    public void deleteByEmail(String email) {
        memberRepository.delete(dtoToEntity(findByEmail(email)));
    }

    @Override
    public void updateMemberState(String email, int state) {
        memberRepository.updateMemberState(memberRepository.findByLoginId(email).getSeq(), state);
    }
}
