package kr.co.morandi.backend.domain.algorithm;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.contenttype.ContentType;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomSetsAlgorithm {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customSetsAlgorithmId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONTENT_TYPE_ID")
    private ContentType contentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ALGORITHM_ID")
    private Algorithm algorithm;
}
