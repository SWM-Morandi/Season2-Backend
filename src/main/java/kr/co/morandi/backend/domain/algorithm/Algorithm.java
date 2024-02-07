package kr.co.morandi.backend.domain.algorithm;

import com.sun.jdi.PrimitiveValue;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Algorithm {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long algorithmId;

    private String algorithmName;
}
