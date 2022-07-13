package com.diary.inn.InnDiary.work.service.info;

import com.diary.inn.InnDiary.work.domain.info.Member;
import com.diary.inn.InnDiary.work.entity.info.MemberEntity;

public interface MemberConvertService {
    Member entityToDto(MemberEntity memberEntity);
    MemberEntity dtoToEntity(Member member);
}
