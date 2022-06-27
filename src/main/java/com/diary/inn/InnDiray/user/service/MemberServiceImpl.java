package com.diary.inn.InnDiray.user.service;

import com.diary.inn.InnDiray.user.domain.Member;
import com.diary.inn.InnDiray.user.entity.MemberEntity;
import com.diary.inn.InnDiray.user.repository.MemberRepository;
import com.diary.inn.InnDiray.user.repository.SearchWithPageMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService, MemberConvertService {
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
    public List<Member> memberAll() {
        List<MemberEntity> search = memberRepository.findAll();
        List<Member> result = new ArrayList<>();

        for (MemberEntity entity : search) {
            result.add(entityToDto(entity));
        }

        return result;
    }
}
