package kr.co.morandi.backend.domain.record;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.BaseEntity;
import kr.co.morandi.backend.domain.defense.model.Defense;
import kr.co.morandi.backend.domain.detail.Detail;
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
import java.util.Map;

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

    protected abstract Detail createDetail(Member member, Long sequenceNumber, Problem problem, Record record, Defense defense);

    protected Record(LocalDateTime testDate, Defense defense, Member member, Map<Long, Problem> problems) {
        this.testDate = testDate;
        this.defense = defense;
        this.member = member;
        this.details = problems.entrySet().stream()
                .map(problem -> this.createDetail(member, problem.getKey(), problem.getValue(), this, defense))
                .toList();
    }
}
