package kr.co.morandi.backend.domain.contenttype.stagedefense;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.co.morandi.backend.domain.contenttype.ContentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("StageDefense")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StageDefense extends ContentType {

    private Double averageStage;

    private String startDifficulty;

    private String endDifficulty;

    @Enumerated(EnumType.STRING)
    private StageDefenseType stageDefenseType;

    private Long timeLimit;

    private Long minSolvedCount;

    private Long maxSolvedCount;
}
