package kr.co.morandi.backend.judgement.domain.model.submit;

import jakarta.persistence.*;
import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.common.model.BaseEntity;
import kr.co.morandi.backend.defense_record.domain.model.record.Detail;
import kr.co.morandi.backend.judgement.domain.error.SubmitErrorCode;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Submit extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long submitId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Detail detail;

    @Embedded
    private SourceCode sourceCode;

    @Enumerated(EnumType.STRING)
    private SubmitVisibility submitVisibility;

    @Embedded
    private JudgementResult judgementResult;

    private LocalDateTime submitDateTime;

    public void trySolveProblem() {
        this.detail.trySolveProblem(this.getSubmitId(), submitDateTime);
    }

    protected void updateJudgementResult(JudgementResult judgementResult) {
        // 제출 하나의 결과는 한 번 정해지면 변하지 않음
        this.judgementResult.canUpdateJudgementResult();
        this.judgementResult = judgementResult;

        if(judgementResult.isAccepted()) {
            this.detail.trySolveProblem(this.getSubmitId(), submitDateTime);
        }
    }

    protected Submit(Member member, Detail detail, SourceCode sourceCode,
                     LocalDateTime submitDateTime, SubmitVisibility submitVisibility) {
        this.member = member;
        validateSubmitDateTime(submitDateTime);
        this.submitDateTime = submitDateTime;

        validateDetail(detail);
        this.detail = detail;
        detail.increaseSubmitCount();

        validateSubmitCode(sourceCode);
        this.sourceCode = sourceCode;

        validateSubmitVisibility(submitVisibility);
        this.submitVisibility = submitVisibility;

        this.judgementResult = JudgementResult.submit();
    }
    private void validateSubmitDateTime(LocalDateTime submitDateTime) {
        if(submitDateTime == null)
            throw new MorandiException(SubmitErrorCode.SUBMIT_DATE_TIME_IS_NULL);
    }

    private void validateDetail(Detail detail) {
        if(detail == null)
            throw new MorandiException(SubmitErrorCode.DETAIL_IS_NULL);
    }
    private void validateSubmitCode(SourceCode sourceCode) {
        if(sourceCode == null)
            throw new MorandiException(SubmitErrorCode.SUBMIT_CODE_IS_NULL);
    }
    private void validateSubmitVisibility(SubmitVisibility submitVisibility) {
        if(submitVisibility == null)
            throw new MorandiException(SubmitErrorCode.VISIBILITY_NOT_NULL);
    }
}
