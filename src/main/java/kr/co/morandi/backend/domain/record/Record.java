package kr.co.morandi.backend.domain.record;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.BaseEntity;
import kr.co.morandi.backend.domain.detail.Detail;
import kr.co.morandi.backend.domain.defense.Defense;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.problem.Problem;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Record extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long detailId;

    private LocalDateTime testDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Defense defense;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder.Default
    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL)
    private List<Detail> details = new ArrayList<>();
    protected abstract Detail createDetail(Member member, Problem problem,
                                           Record record, Defense defense);
    protected Record(LocalDateTime testDate, Defense defense, Member member, List<Problem> problems) {
        this.testDate = testDate;
        this.defense = defense;
        this.member = member;
        this.details = problems.stream()
                .map(problem -> this.createDetail(member, problem, this, defense))
                .toList();
    }
}
