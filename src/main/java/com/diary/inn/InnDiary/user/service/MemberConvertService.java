package com.diary.inn.InnDiary.user.service;

import com.diary.inn.InnDiary.user.domain.Member;
import com.diary.inn.InnDiary.user.entity.MemberEntity;

public interface MemberConvertService {
    Member entityToDto(MemberEntity memberEntity);
    MemberEntity dtoToEntity(Member member);
}
