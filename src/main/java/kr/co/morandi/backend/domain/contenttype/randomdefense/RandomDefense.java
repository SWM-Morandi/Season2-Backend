package kr.co.morandi.backend.domain.contenttype.randomdefense;

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
@DiscriminatorValue("RandomDefense")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RandomDefense extends ContentType {

    private String startDifficulty;

    private String endDifficulty;

    @Enumerated(EnumType.STRING)
    private RandomDefenseType defenseType;

    private Long problemCount;

    private Long timeLimit;

    private Long minSolvedCount;

    private Long maxSolvedCount;
}
