package com.diary.inn.InnDiary.info.service;

import com.diary.inn.InnDiary.info.domain.Member;
import com.diary.inn.InnDiary.info.entity.MemberEntity;

public interface MemberConvertService {
    Member entityToDto(MemberEntity memberEntity);
    MemberEntity dtoToEntity(Member member);
}
