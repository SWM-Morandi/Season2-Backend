package kr.co.morandi.backend.defense_record.domain.model.record;

import jakarta.persistence.*;
import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.common.model.BaseEntity;
import kr.co.morandi.backend.defense_information.domain.model.defense.Defense;
import kr.co.morandi.backend.defense_record.domain.error.RecordErrorCode;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Record<T extends Detail> extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;

    private LocalDateTime testDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Defense defense;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL, targetEntity = Detail.class)
    private List<T> details = new ArrayList<>();

    private Long totalSolvedTime;

    @Enumerated(EnumType.STRING)
    private RecordStatus status;

    private static final Long INITIAL_TOTAL_SOLVED_TIME = 0L;

    public T getDetail(Long sequenceNumber) {
        return this.details.stream()
                .filter(detail -> detail.getSequenceNumber().equals(sequenceNumber))
                .findFirst()
                .orElseThrow(() -> new MorandiException(RecordErrorCode.DETAIL_NOT_FOUND));
    }

    public Problem getProblem(Long sequenceNumber) {
        return getDetail(sequenceNumber).getProblem();
    }
    public boolean isTerminated() {
        return this.status.equals(RecordStatus.COMPLETED);
    }

    public boolean terminteDefense() {
        if(this.status.equals(RecordStatus.COMPLETED)) {
            throw new MorandiException(RecordErrorCode.RECORD_ALREADY_TERMINATED);
        }
        this.status = RecordStatus.COMPLETED;
        return true;
    }
    public void addTotalSolvedTime(Long totalSolvedTime) {
        this.totalSolvedTime += totalSolvedTime;
    }
    protected abstract T createDetail(Member member, Long sequenceNumber, Problem problem, Record<T> records, Defense defense);

    protected Record(LocalDateTime testDate, Defense defense, Member member, Map<Long, Problem> problems) {
        this.testDate = testDate;
        this.defense = defense;
        this.member = member;
        this.status = RecordStatus.IN_PROGRESS;
        this.totalSolvedTime = INITIAL_TOTAL_SOLVED_TIME;
        this.details = problems.entrySet().stream()
                .map(problem -> this.createDetail(member, problem.getKey(), problem.getValue(), this, defense))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
