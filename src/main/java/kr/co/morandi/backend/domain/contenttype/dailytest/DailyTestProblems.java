package kr.co.morandi.backend.domain.contenttype.dailytest;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.BaseEntity;
import kr.co.morandi.backend.domain.problem.Problem;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyTestProblems extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dailyProblemsId;

    @ManyToOne(fetch = FetchType.LAZY)
    private DailyTest dailyTest;

    @ManyToOne(fetch = FetchType.LAZY)
    private Problem problem;

    private Long submitCount;

    private Long solvedCount;

    @Builder
    private DailyTestProblems(DailyTest dailyTest, Problem problem, Long submitCount, Long solvedCount) {
        this.dailyTest = dailyTest;
        this.problem = problem;
        this.submitCount = submitCount;
        this.solvedCount = solvedCount;
    }
}
