package kr.co.morandi.backend.domain.algorithm;

import com.sun.jdi.PrimitiveValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Algorithm {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long algorithmId;

    private String algorithmName;

    @Builder
    private Algorithm(Long algorithmId, String algorithmName) {
        this.algorithmId = algorithmId;
        this.algorithmName = algorithmName;
    }
}
