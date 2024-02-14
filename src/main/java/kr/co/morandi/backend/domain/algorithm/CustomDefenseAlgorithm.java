package kr.co.morandi.backend.domain.algorithm;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.contenttype.ContentType;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomDefenseAlgorithm {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customSetsAlgorithmId;

    @ManyToOne(fetch = FetchType.LAZY)
    private ContentType contentType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Algorithm algorithm;
}
