package kr.co.morandi.backend.judgement.domain.model.submit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.judgement.domain.error.JudgementResultErrorCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JudgementResult {

    @Enumerated(EnumType.STRING)
    private JudgementStatus judgementStatus;

    @Column(name = "memory")
    private Integer memory;

    @Column(name = "time")
    private Integer time;

    private static final Integer INITIAL_MEMORY = 0;
    private static final Integer INITIAL_TIME = 0;

    public static JudgementResult submit() {
        return new JudgementResult(JudgementStatus.SUBMITTED, INITIAL_MEMORY, INITIAL_TIME);
    }
    public static JudgementResult accepted(Integer memory, Integer time) {
        return new JudgementResult(JudgementStatus.ACCEPTED, memory, time);
    }
    public static JudgementResult rejected(JudgementStatus judgementStatus) {
        return new JudgementResult(judgementStatus, INITIAL_MEMORY, INITIAL_TIME);
    }
    public void canUpdateJudgementResult() {
        if(!judgementStatus.equals(JudgementStatus.SUBMITTED))
            throw new MorandiException(JudgementResultErrorCode.ALREADY_JUDGED);
    }
    @Builder
    private JudgementResult(JudgementStatus judgementStatus, Integer memory, Integer time) {
        if(judgementStatus == null)
            throw new MorandiException(JudgementResultErrorCode.JUDGEMENT_RESULT_NOT_FOUND);
        validateMemory(memory);
        validateTime(time);
        validateCanExistTimeAndMemory(judgementStatus, memory, time);

        this.judgementStatus = judgementStatus;
        this.memory = memory;
        this.time = time;
    }
    private void validateCanExistTimeAndMemory(JudgementStatus judgementStatus, Integer memory, Integer time) {
        if(!judgementStatus.equals(JudgementStatus.ACCEPTED) && (memory != 0 || time != 0))
            throw new MorandiException(JudgementResultErrorCode.NOT_ACCEPTED_CANNOT_HAVE_MEMORY_AND_TIME);
    }

    private void validateMemory(Integer memory) {
        if(memory == null)
            throw new MorandiException(JudgementResultErrorCode.MEMORY_IS_NULL);
        if(memory < 0)
            throw new MorandiException(JudgementResultErrorCode.MEMORY_IS_NEGATIVE);
    }
    private void validateTime(Integer time) {
        if(time == null)
            throw new MorandiException(JudgementResultErrorCode.TIME_IS_NULL);
        if(time < 0)
            throw new MorandiException(JudgementResultErrorCode.TIME_IS_NEGATIVE);
    }
}
