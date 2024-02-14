package kr.co.morandi.backend.domain.contenttype.customdefense;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomDefenseProblems {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customProblemsId;

    private Long submitCount;

    private Long solvedCount;

    @ManyToOne(fetch = FetchType.LAZY)
    private CustomDefense customDefense;

    @Builder
    private CustomDefenseProblems(Long customProblemsId, Long submitCount, Long solvedCount, CustomDefense customDefense) {
        this.customProblemsId = customProblemsId;
        this.submitCount = submitCount;
        this.solvedCount = solvedCount;
        this.customDefense = customDefense;
    }
}
