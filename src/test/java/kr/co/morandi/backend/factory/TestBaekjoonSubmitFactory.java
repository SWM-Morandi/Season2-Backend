package kr.co.morandi.backend.factory;

import kr.co.morandi.backend.defense_record.domain.model.record.Detail;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.submit.BaekjoonSubmit;
import kr.co.morandi.backend.judgement.domain.model.submit.SourceCode;
import kr.co.morandi.backend.member_management.domain.model.member.Member;

import java.time.LocalDateTime;

import static kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language.JAVA;
import static kr.co.morandi.backend.judgement.domain.model.submit.SubmitVisibility.CLOSE;

public class TestBaekjoonSubmitFactory {
    public static BaekjoonSubmit createSubmit(Member member, Detail detail, LocalDateTime submitTime) {
        return BaekjoonSubmit.builder()
                .submitDateTime(submitTime)
                .submitVisibility(CLOSE)
                .member(member)
                .detail(detail)
                .sourceCode(SourceCode.builder()
                        .sourceCode("sourceCode")
                        .language(JAVA)
                        .build())
                .build();
    }
}
