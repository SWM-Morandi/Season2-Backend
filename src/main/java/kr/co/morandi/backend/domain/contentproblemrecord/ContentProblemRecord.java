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
}
