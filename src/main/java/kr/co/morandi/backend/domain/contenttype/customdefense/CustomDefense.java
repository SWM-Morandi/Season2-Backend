package kr.co.morandi.backend.domain.contenttype.customdefense;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.contenttype.ContentType;
import kr.co.morandi.backend.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("CustomDefense")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomDefense extends ContentType {

    private LocalDateTime testDate;

    private Long problemCount;

    private String information;

    @Enumerated(EnumType.STRING)
    private Visibility visibility;

    private Long goodCount;

    private Long badCount;

    private String difficulty;

    private Long timeLimit;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
