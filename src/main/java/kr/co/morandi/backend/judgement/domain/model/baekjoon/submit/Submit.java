package kr.co.morandi.backend.judgement.domain.model.baekjoon.submit;

import jakarta.persistence.*;
import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.common.model.BaseEntity;
import kr.co.morandi.backend.defense_record.domain.model.record.Detail;
import kr.co.morandi.backend.judgement.domain.error.SubmitErrorCode;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorColumn
public abstract class Submit extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long submitId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)//, targetEntity = Detail.class)
    private Detail detail;

    @Embedded
    private SubmitCode submitCode;

    @Enumerated(EnumType.STRING)
    private SubmitVisibility submitVisibility;

    @Embedded
    private JudgementResult judgementResult;

    private Integer trialNumber;

    protected void updateStatusToAccepted(Integer memory, Integer time) {
        this.judgementResult.updateToAccepted(memory, time);
    }

    protected Submit(Member member, Detail detail, SubmitCode submitCode,
                     SubmitVisibility submitVisibility, Integer trialNumber) {
        this.member = member;

        validateDetail(detail);
        this.detail = detail;

        validateSubmitCode(submitCode);
        this.submitCode = submitCode;

        validateSubmitVisibility(submitVisibility);
        this.submitVisibility = submitVisibility;

        this.judgementResult = JudgementResult.submit();

        validateTrialNumber(trialNumber);
        this.trialNumber = trialNumber;
    }

    private void validateDetail(Detail detail) {
        if(detail == null)
            throw new MorandiException(SubmitErrorCode.DETAIL_IS_NULL);
    }
    private void validateSubmitCode(SubmitCode submitCode) {
        if(submitCode == null)
            throw new MorandiException(SubmitErrorCode.SUBMIT_CODE_IS_NULL);
    }
    private void validateSubmitVisibility(SubmitVisibility submitVisibility) {
        if(submitVisibility == null)
            throw new MorandiException(SubmitErrorCode.VISIBILITY_NOT_NULL);
    }
    private void validateTrialNumber(Integer trialNumber) {
        if(trialNumber == null)
            throw new MorandiException(SubmitErrorCode.TRIAL_NUMBER_IS_NULL);
        if(trialNumber < 0)
            throw new MorandiException(SubmitErrorCode.TRIAL_NUMBER_IS_NEGATIVE);
    }
}
