package kr.co.morandi.backend.judgement.domain.model.baekjoon.submit;

import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.defense_record.domain.model.record.Detail;
import kr.co.morandi.backend.judgement.domain.error.JudgementResultErrorCode;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.result.BaekjoonCorrectInfo;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.result.ResultType;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class BaekjoonSubmit extends Submit {

    @Enumerated(EnumType.STRING)
    private ResultType result;

    @Embedded
    private BaekjoonCorrectInfo baekjoonCorrectInfo;

    public static BaekjoonSubmit of(Member member, Detail detail, SubmitCode submitCode,
                                    SubmitVisibility submitVisibility) {
        return new BaekjoonSubmit(member, detail, submitCode, submitVisibility);
    }

    public void updateResult(Integer code, BaekjoonCorrectInfo baekjoonCorrectInfo) {
        canUpdateResult();
        this.result = ResultType.fromCode(code);

        canUpdateBaekjoonCorretInfo();
        this.baekjoonCorrectInfo = baekjoonCorrectInfo;
    }

    private void canUpdateResult() {
        if(result.equals(ResultType.CORRECT)) {
            throw new MorandiException(JudgementResultErrorCode.RESULT_INFO_WHEN_CORRECT);
        }
    }
    private void canUpdateBaekjoonCorretInfo() {
        if(!result.equals(ResultType.CORRECT)) {
            throw new MorandiException(JudgementResultErrorCode.CORRECT_INFO_WHEN_NOT_CORRECT);
        }
    }

    protected BaekjoonSubmit(Member member, Detail detail, SubmitCode submitCode,
                             SubmitVisibility submitVisibility) {
        super(member, detail, submitCode, submitVisibility);
        this.result = ResultType.SUBMITTED;
        this.baekjoonCorrectInfo = BaekjoonCorrectInfo.initial();
    }
}
