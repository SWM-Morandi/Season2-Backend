package kr.co.morandi.backend.domain.contenttype.dailytest;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.BaseEntity;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyTestProblems extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dailyProblemsId;

    @ManyToOne(fetch = FetchType.LAZY)
    private DailyTest dailyTest;

    private Long submitCount;

    private Long solvedCount;
}
