package kr.co.morandi.backend.domain.contenttype.random.randomdefense;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.contenttype.ContentType;
import kr.co.morandi.backend.domain.contenttype.random.randomcriteria.RandomCriteria;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("RandomDefense")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RandomDefense extends ContentType {

    @Embedded
    private RandomCriteria randomCriteria;

    private Long problemCount;

    private Long timeLimit;


}
