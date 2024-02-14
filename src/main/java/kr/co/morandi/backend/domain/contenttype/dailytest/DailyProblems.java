package kr.co.morandi.backend.domain.contenttype.dailytest;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyProblems {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dailyProblemsId;

    @ManyToOne(fetch = FetchType.LAZY)
    private DailyTest dailyTest;

    private Long submitCount;

    private Long solvedCount;
}
