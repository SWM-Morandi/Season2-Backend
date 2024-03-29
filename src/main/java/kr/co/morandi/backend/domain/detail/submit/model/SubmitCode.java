package kr.co.morandi.backend.domain.detail.submit.model;

import jakarta.persistence.*;
import kr.co.morandi.backend.domain.detail.Detail;
import kr.co.morandi.backend.domain.member.model.Member;
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
    private Detail detail;

    @Builder
    private SubmitCode(String submitCodeLink, Member member, Detail detail) {
        this.submitCodeLink = submitCodeLink;
        this.member = member;
        this.detail = detail;
    }
}
