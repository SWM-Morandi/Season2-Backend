package kr.co.morandi.backend.domain.algorithm;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import kr.co.morandi.backend.domain.BaseEntity;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Algorithm extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long algorithmId;

    private Integer bojTagId;

    private String algorithmKey;

    private String algorithmName;

    @Builder
    private Algorithm(Integer bojTagId, String algorithmKey, String algorithmName) {
        this.bojTagId = bojTagId;
        this.algorithmKey = algorithmKey;
        this.algorithmName = algorithmName;
    }
}
