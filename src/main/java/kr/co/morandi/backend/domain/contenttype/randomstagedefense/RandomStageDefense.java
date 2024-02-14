package kr.co.morandi.backend.domain.contenttype.randomstagedefense;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.contenttype.ContentType;
import kr.co.morandi.backend.domain.contenttype.randomcriteria.RandomCriteria;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("StageDefense")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RandomStageDefense extends ContentType {

    private Double averageStage;

    @Embedded
    private RandomCriteria randomCriteria;

    private Long timeLimit;

}
