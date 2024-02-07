package kr.co.morandi.backend.domain.contenttype.customsets;

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
public class CustomProblems {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customProblemsId;

    private Long submitCount;

    private Long solvedCount;

    @ManyToOne
    @JoinColumn(name = "CONTENT_TYPE_ID")
    private CustomSets customSets;
}
