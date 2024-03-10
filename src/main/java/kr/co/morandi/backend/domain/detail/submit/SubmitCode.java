package kr.co.morandi.backend.domain.detail.submit;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.detail.ContentProblemRecord;
import kr.co.morandi.backend.domain.member.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SubmitCode {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long submitRecordId;

    private String submitCodeLink;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private ContentProblemRecord contentProblemRecord;

    @Builder
    private SubmitCode(String submitCodeLink, Member member, ContentProblemRecord contentProblemRecord) {
        this.submitCodeLink = submitCodeLink;
        this.member = member;
        this.contentProblemRecord = contentProblemRecord;
    }
}
