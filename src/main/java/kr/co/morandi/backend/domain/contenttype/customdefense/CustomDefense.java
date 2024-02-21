package kr.co.morandi.backend.domain.contenttype.customdefense;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.contenttype.ContentType;
import kr.co.morandi.backend.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("CustomDefense")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomDefense extends ContentType {

    private LocalDateTime testDate;

    private Long problemCount;

    private String description;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    @Enumerated(EnumType.STRING)
    private DefenseTier defenseTier;

    private Long timeLimit;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
