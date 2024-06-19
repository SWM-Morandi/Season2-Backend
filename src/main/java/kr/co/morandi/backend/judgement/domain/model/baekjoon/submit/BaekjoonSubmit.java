package kr.co.morandi.backend.judgement.domain.model.baekjoon.submit;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import kr.co.morandi.backend.defense_record.domain.model.record.Detail;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.result.BaekjoonJudgementResult;
import kr.co.morandi.backend.judgement.domain.model.submit.JudgementResult;
import kr.co.morandi.backend.judgement.domain.model.submit.Submit;
import kr.co.morandi.backend.judgement.domain.model.submit.SourceCode;
import kr.co.morandi.backend.judgement.domain.model.submit.SubmitVisibility;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@DiscriminatorValue("BaekjoonSubmit")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BaekjoonSubmit extends Submit {

    @Embedded
    private BaekjoonJudgementResult baekjoonJudgementResult;

    public static BaekjoonSubmit submit(Member member, Detail detail, SourceCode sourceCode,
                                        LocalDateTime submitDateTime, SubmitVisibility submitVisibility) {
        return new BaekjoonSubmit(member, detail, sourceCode, submitDateTime, submitVisibility, null);
    }

    public void updateJudgementResult(JudgementResult judgementResult, BaekjoonJudgementResult baekjoonJudgementResult) {
        super.updateJudgementResult(judgementResult);
        this.baekjoonJudgementResult = baekjoonJudgementResult;
    }
    @Builder
    private BaekjoonSubmit(Member member, Detail detail, SourceCode sourceCode,
                           LocalDateTime submitDateTime, SubmitVisibility submitVisibility, BaekjoonJudgementResult baekjoonJudgementResult) {
        super(member, detail, sourceCode, submitDateTime, submitVisibility);
        this.baekjoonJudgementResult = baekjoonJudgementResult;
    }
}
