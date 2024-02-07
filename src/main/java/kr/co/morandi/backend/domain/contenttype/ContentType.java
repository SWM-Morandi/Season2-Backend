package kr.co.morandi.backend.domain.contenttype;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContentType {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contentTypeId;

    private String contentName;

    private Long attemptCount;

}
