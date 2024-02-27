package kr.co.morandi.backend.domain.contenttype.dailytest;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.BaseEntity;
import kr.co.morandi.backend.domain.contenttype.customdefense.CustomDefenseProblems;
import kr.co.morandi.backend.domain.problem.Problem;
import lombok.*;
import org.springframework.security.core.parameters.P;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyTestProblems extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dailyProblemsId;

    private Long submitCount;

    private Long solvedCount;

    @ManyToOne(fetch = FetchType.LAZY)
    private DailyTest dailyTest;

    @ManyToOne(fetch = FetchType.LAZY)
    private Problem problem;
    public DailyTestProblems(DailyTest dailyTest, Problem problem) {
        this.dailyTest = dailyTest;
        this.problem = problem;
        this.submitCount = 0L;
        this.solvedCount = 0L;
    }
}
