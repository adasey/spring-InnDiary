package com.diary.inn.InnDiray.user.service;

import com.diary.inn.InnDiray.user.domain.Member;
import com.diary.inn.InnDiray.user.entity.MemberEntity;

public interface MemberConvertService {
    Member entityToDto(MemberEntity memberEntity);
    MemberEntity dtoToEntity(Member member);
}
