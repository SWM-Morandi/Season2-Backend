package kr.co.morandi.backend.domain.contentproblemrecord;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.BaseEntity;
import kr.co.morandi.backend.domain.contentrecord.ContentRecord;
import kr.co.morandi.backend.domain.contenttype.ContentType;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.problem.Problem;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ContentProblemRecord extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contentProblemRecordId;

    private Boolean isSolved;

    private Long submitCount;

    private String solvedCode;

    @ManyToOne(fetch = FetchType.LAZY)
    private ContentType contentType;

    @ManyToOne(fetch = FetchType.LAZY)
    private ContentRecord contentRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Problem problem;

    private static final Long INITIAL_SUBMIT_COUNT = 0L;
    private static final Boolean INITIAL_IS_SOLVED = false;

    protected ContentProblemRecord(Member member, Problem problem,
                                ContentRecord contentRecord, ContentType contentType) {
        this.isSolved = INITIAL_IS_SOLVED;
        this.submitCount = INITIAL_SUBMIT_COUNT;
        this.solvedCode = null;
        this.contentType = contentType;
        this.contentRecord = contentRecord;
        this.member = member;
        this.problem = problem;
    }
}
