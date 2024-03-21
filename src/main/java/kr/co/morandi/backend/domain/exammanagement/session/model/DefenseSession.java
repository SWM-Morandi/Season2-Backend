package kr.co.morandi.backend.domain.exammanagement.session.model;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.exammanagement.sessiondetail.model.SessionDetail;
import kr.co.morandi.backend.domain.member.model.Member;
import kr.co.morandi.backend.domain.record.Record;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static kr.co.morandi.backend.domain.exammanagement.session.model.ExamStatus.IN_PROGRESS;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DefenseSession {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long defenseSessionId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Record<?> record;

    @OneToMany(mappedBy = "defenseSession", cascade = CascadeType.ALL)
    private List<SessionDetail> sessionDetails = new ArrayList<>();

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private Long lastAccessProblemNumber;

    private LocalDateTime lastAccessDateTime;

    @Enumerated(EnumType.STRING)
    private ExamStatus examStatus;

    private static final Long INITIAL_ACCESS_PROBLEM_NUMBER = 1L;

    public DefenseSession tryMoreProblem(Long problemNumber, LocalDateTime accessDateTime) {
        if (examStatus != IN_PROGRESS || accessDateTime.isAfter(endDateTime)) {
            throw new IllegalStateException("이미 종료된 시험입니다.");
        }

        // 이미 있는 시험이라면
        if (sessionDetails.stream()
                .anyMatch(sessionDetail -> sessionDetail.getProblemNumber().equals(problemNumber))) {
            return this;
        }
        sessionDetails.add(SessionDetail.create(this, problemNumber));
        lastAccessProblemNumber = problemNumber;
        lastAccessDateTime = accessDateTime;

        return this;
    }
    public static DefenseSession startSession(Member member, Record<?> record, Set<Long> problemNumbers, LocalDateTime startDateTime) {
        return new DefenseSession(member, record, problemNumbers, startDateTime);
    }
    private DefenseSession(Member member, Record<?> record, Set<Long> problemNumbers, LocalDateTime startDateTime) {
        if(problemNumbers==null || problemNumbers.isEmpty())
            throw new IllegalArgumentException("문제 번호가 없습니다.");
        this.member = member;
        this.record = record;
        this.sessionDetails = problemNumbers.stream()
                .map(problemNumber -> SessionDetail.create(this, problemNumber))
                .collect(Collectors.toCollection(ArrayList::new));
        this.startDateTime = startDateTime;
        this.endDateTime = record.getDefense().getEndTime(startDateTime);
        this.examStatus = IN_PROGRESS;
        this.lastAccessDateTime = startDateTime;
        this.lastAccessProblemNumber = problemNumbers.stream()
                .findFirst()
                .orElse(INITIAL_ACCESS_PROBLEM_NUMBER);
    }

}
