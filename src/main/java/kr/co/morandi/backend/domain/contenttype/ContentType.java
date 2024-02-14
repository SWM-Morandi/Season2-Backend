package kr.co.morandi.backend.domain.contenttype;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ContentType {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contentTypeId;

    private String contentName;

    private Long attemptCount;

}
