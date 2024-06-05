package kr.co.morandi.backend.judgement.domain.model.baekjoon.submit;

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
        return new JudgementResult(INITIAL_MEMORY, INITIAL_TIME);
    }

    public void updateToAccepted(Integer memory, Integer time) {
        if(judgementStatus.equals(JudgementStatus.ACCEPTED))
            throw new MorandiException(JudgementResultErrorCode.ALREADY_ACCEPTED);

        this.judgementStatus = JudgementStatus.ACCEPTED;

        validateMemory(memory);
        this.memory = memory;

        validateTime(time);
        this.time = time;
    }

    @Builder
    private JudgementResult(Integer memory, Integer time) {
        this.judgementStatus = JudgementStatus.SUBMITTED;

        validateMemory(memory);
        this.memory = memory;

        validateTime(time);
        this.time = time;
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
