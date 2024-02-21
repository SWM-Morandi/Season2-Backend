package kr.co.morandi.backend.domain.contenttype.randomdefense;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.contenttype.ContentType;
import kr.co.morandi.backend.domain.contenttype.randomdefense.randomcriteria.RandomCriteria;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("StageDefense")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RandomStageDefense extends ContentType {

    @Embedded
    private RandomCriteria randomCriteria;

    private Double averageStage;

    private Long timeLimit;

}
