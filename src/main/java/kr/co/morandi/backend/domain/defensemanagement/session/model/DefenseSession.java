package kr.co.morandi.backend.domain.defensemanagement.session.model;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.BaseEntity;
import kr.co.morandi.backend.domain.defense.DefenseType;
import kr.co.morandi.backend.domain.defensemanagement.sessiondetail.model.SessionDetail;
import kr.co.morandi.backend.domain.member.model.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static kr.co.morandi.backend.domain.defensemanagement.session.model.ExamStatus.COMPLETED;
import static kr.co.morandi.backend.domain.defensemanagement.session.model.ExamStatus.IN_PROGRESS;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DefenseSession extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long defenseSessionId;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private Long recordId;

    @OneToMany(mappedBy = "defenseSession", cascade = CascadeType.ALL)
    private List<SessionDetail> sessionDetails = new ArrayList<>();

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private Long lastAccessProblemNumber;

    private LocalDateTime lastAccessDateTime;

    @Enumerated(EnumType.STRING)
    private DefenseType defenseType;

    @Enumerated(EnumType.STRING)
    private ExamStatus examStatus;

    private static final Long INITIAL_ACCESS_PROBLEM_NUMBER = 1L;

    public SessionDetail getSessionDetail(Long problemNumber) {
        return getSessionDetails().stream()
                .filter(detail -> Objects.equals(detail.getProblemNumber(), problemNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 문제가 없습니다."));
    }
    public boolean hasTriedProblem(Long problemNumber) {
        return getSessionDetails().stream()
                .anyMatch(detail -> Objects.equals(detail.getProblemNumber(), problemNumber));
    }
    public void tryMoreProblem(Long problemNumber, LocalDateTime accessDateTime) {
        if (examStatus == COMPLETED || accessDateTime.isAfter(endDateTime)) {
            throw new IllegalStateException("이미 종료된 시험입니다.");
        }
        // 이미 있는 시험이라면
        if (sessionDetails.stream()
                .anyMatch(sessionDetail -> sessionDetail.getProblemNumber().equals(problemNumber))) {
            return ;
        }
        sessionDetails.add(SessionDetail.create(this, problemNumber));
        lastAccessProblemNumber = problemNumber;
        lastAccessDateTime = accessDateTime;

    }
    public static DefenseSession startSession(Member member, Long recordId, DefenseType defenseType, Set<Long> problemNumbers,
                                              LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return new DefenseSession(member, recordId, defenseType, problemNumbers, startDateTime, endDateTime);
    }
    private DefenseSession(Member member, Long recordId, DefenseType defenseType, Set<Long> problemNumbers,
                           LocalDateTime startDateTime, LocalDateTime endDateTime) {
        if(problemNumbers==null || problemNumbers.isEmpty())
            throw new IllegalArgumentException("문제 번호가 없습니다.");
        this.member = member;
        this.recordId = recordId;
        this.defenseType = defenseType;
        this.sessionDetails = problemNumbers.stream()
                .map(problemNumber -> SessionDetail.create(this, problemNumber))
                .collect(Collectors.toCollection(ArrayList::new));
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.examStatus = IN_PROGRESS;
        this.lastAccessDateTime = startDateTime;
        this.lastAccessProblemNumber = problemNumbers.stream()
                .findFirst()
                .orElse(INITIAL_ACCESS_PROBLEM_NUMBER);
    }

}
