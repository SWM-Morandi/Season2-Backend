package kr.co.morandi.backend.domain.contenttype;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ContentType extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contentTypeId;

    private String contentName;

    private Long attemptCount;

}
