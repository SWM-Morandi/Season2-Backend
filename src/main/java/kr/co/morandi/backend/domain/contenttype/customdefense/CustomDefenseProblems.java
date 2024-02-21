package kr.co.morandi.backend.domain.contenttype.customdefense;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.BaseEntity;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomDefenseProblems extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customProblemsId;

    private Long submitCount;

    private Long solvedCount;

    @ManyToOne(fetch = FetchType.LAZY)
    private CustomDefense customDefense;

    @Builder
    private CustomDefenseProblems(Long submitCount, Long solvedCount, CustomDefense customDefense) {
        this.submitCount = submitCount;
        this.solvedCount = solvedCount;
        this.customDefense = customDefense;
    }
}
