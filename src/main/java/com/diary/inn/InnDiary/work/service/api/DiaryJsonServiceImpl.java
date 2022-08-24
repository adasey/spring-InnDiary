package com.diary.inn.InnDiary.work.service.api;

import com.diary.inn.InnDiary.work.domain.api.DiaryJson;
import com.diary.inn.InnDiary.work.domain.info.Member;
import com.diary.inn.InnDiary.work.entity.api.DiaryJsonEntity;
import com.diary.inn.InnDiary.work.entity.info.MemberEntity;
import com.diary.inn.InnDiary.work.repository.api.DiaryJsonRepository;
import com.diary.inn.InnDiary.work.repository.api.ModifyDiaryJsonRepository;
import com.diary.inn.InnDiary.work.service.info.MemberConversionService;
import com.diary.inn.InnDiary.work.service.info.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DiaryJsonServiceImpl implements DiaryJsonService, DiaryJsonConversionService {
    private final DiaryJsonRepository diaryJsonRepository;
    private final ModifyDiaryJsonRepository modifyDiaryJsonRepository;
    private final MemberService memberService;
    private final MemberConversionService memberConversionService;

    @Override
    public DiaryJson entityToDto(DiaryJsonEntity diaryJsonEntity) {
        return DiaryJson.builder()
                .seq(diaryJsonEntity.getSeq())
                .saveTitle(diaryJsonEntity.getSaveTitle())
                .diary(diaryJsonEntity.getDiary())
                .modDate(diaryJsonEntity.getModDate())
                .build();
    }

    @Override
    public DiaryJson entityToDto(DiaryJsonEntity diaryJsonEntity, MemberEntity memberEntity) {
        return DiaryJson.builder()
                .seq(diaryJsonEntity.getSeq())
                .memberSeq(memberEntity.getSeq())
                .memberState(memberEntity.getState())
                .memberCompany(memberEntity.getCompany())
                .memberId(memberEntity.getLoginId())
                .saveTitle(diaryJsonEntity.getSaveTitle())
                .diary(diaryJsonEntity.getDiary())
                .modDate(diaryJsonEntity.getModDate())
                .build();
    }

    @Override
    public DiaryJsonEntity dtoToEntity(DiaryJson diaryJson) {
        MemberEntity memberEntity = MemberEntity.builder()
                .seq(diaryJson.getMemberSeq())
                .build();

        return DiaryJsonEntity.builder()
                .seq(diaryJson.getSeq())
                .member(memberEntity)
                .saveTitle(diaryJson.getSaveTitle())
                .diary(diaryJson.getDiary())
                .build();
    }

    @Override
    public Long join(DiaryJson diaryJson) {
        DiaryJsonEntity result = diaryJsonRepository.save(dtoToEntity(diaryJson));
        return result.getSeq();
    }

    @Override
    public void uploadToJson(Long seq, String json) {
        modifyDiaryJsonRepository.updateJson(seq, json);
    }

    @Override
    public void uploadToSaveTitle(Long seq, String saveTitle) {
        modifyDiaryJsonRepository.updateSaveTitle(seq, saveTitle);
    }

    @Override
    public void deleteWithEmailNSave(Member member, String saveTitle) {
        DiaryJsonEntity find = modifyDiaryJsonRepository.findByEmailNCompanyWithSave(memberConversionService.dtoToEntity(member), saveTitle);

        diaryJsonRepository.delete(find);
    }

    @Override
    public DiaryJson savedDiaryFind(Long seq) {
        Optional<DiaryJsonEntity> found = diaryJsonRepository.findById(seq);

        if (found.isPresent()) {
            Member member = memberService.findByEmailNCompany(found.get().getMember().getLoginId(), found.get().getMember().getCompany());

            return entityToDto(found.get(), memberConversionService.dtoToEntity(member));
        }

        return null;
    }

    @Override
    public DiaryJson userDataFindWithSave(Member dto, String saveTitle) {
        DiaryJsonEntity result = modifyDiaryJsonRepository.findByEmailNCompanyWithSave(memberConversionService.dtoToEntity(dto), saveTitle);
        if (result != null) {
            return entityToDto(result, memberConversionService.dtoToEntity(dto));
        }
        else {
            return null;
        }
    }

    @Override
    public List<DiaryJson> userDataFindAll(Member dto) {
        MemberEntity memberEntity = memberConversionService.dtoToEntity(dto);

        List<DiaryJsonEntity> found = modifyDiaryJsonRepository.findAllByEmailNCompany(memberEntity);
        List<DiaryJson> result = new ArrayList<>();

        for (DiaryJsonEntity entity : found) {
            result.add(entityToDto(entity, memberEntity));
        }

        if (result.size() > 0) {
            return result;
        }
        else {
            return null;
        }
    }
}
