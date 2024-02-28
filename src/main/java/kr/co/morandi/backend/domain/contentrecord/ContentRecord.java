package kr.co.morandi.backend.domain.contentrecord;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.BaseEntity;
import kr.co.morandi.backend.domain.contentproblemrecord.ContentProblemRecord;
import kr.co.morandi.backend.domain.contenttype.ContentType;
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
import java.util.stream.Collectors;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ContentRecord extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contentRecordId;

    private LocalDateTime testDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private ContentType contentType;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder.Default
    @OneToMany(mappedBy = "contentRecord", cascade = CascadeType.ALL)
    private List<ContentProblemRecord> contentProblemRecords = new ArrayList<>();

    protected abstract ContentProblemRecord createContentProblemRecord(ContentType contentType, ContentRecord contentRecord, Member member, Problem problem);

    public ContentRecord(LocalDateTime testDate, ContentType contentType, Member member, List<Problem> problems) {
        this.testDate = testDate;
        this.contentType = contentType;
        this.member = member;
        this.contentProblemRecords = problems.stream()
                .map(problem -> this.createContentProblemRecord(contentType, this, member, problem))
                .collect(Collectors.toList());
    }

}
