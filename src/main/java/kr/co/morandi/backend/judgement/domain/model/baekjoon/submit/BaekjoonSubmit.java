package kr.co.morandi.backend.judgement.domain.model.baekjoon.submit;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Embedded;
import kr.co.morandi.backend.defense_record.domain.model.record.Detail;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.result.BaekjoonJudgementResult;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@DiscriminatorValue("BaekjoonSubmit")
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class BaekjoonSubmit extends Submit {

    @Embedded
    private BaekjoonJudgementResult baekjoonJudgementResult;

    public static BaekjoonSubmit submit(Member member, Detail detail, SubmitCode submitCode,
                                        SubmitVisibility submitVisibility, Integer trialNumber) {
        return new BaekjoonSubmit(member, detail, submitCode, submitVisibility, trialNumber, null);
    }

    public void updateStatusToAccepted(Integer memory, Integer time, BaekjoonJudgementResult baekjoonJudgementResult) {
        super.updateStatusToAccepted(memory, time);
        this.baekjoonJudgementResult = baekjoonJudgementResult;
    }
    @Builder
    private BaekjoonSubmit(Member member, Detail detail, SubmitCode submitCode,
                           SubmitVisibility submitVisibility, Integer trialNumber,
                           BaekjoonJudgementResult baekjoonJudgementResult) {
        super(member, detail, submitCode, submitVisibility, trialNumber);
        this.baekjoonJudgementResult = baekjoonJudgementResult;
    }
}
