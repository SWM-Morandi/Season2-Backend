package kr.co.morandi.backend.defense_management.domain.model.tempcode.model;

import jakarta.persistence.*;
import kr.co.morandi.backend.common.model.BaseEntity;
import kr.co.morandi.backend.defense_management.domain.model.session.SessionDetail;
import kr.co.morandi.backend.judgement.domain.model.submit.SourceCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TempCode extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tempCodeId;

    @ManyToOne(fetch = FetchType.LAZY)
    private SessionDetail sessionDetail;

    @Embedded
    private SourceCode sourceCode;

    public Language getLanguage() {
        return sourceCode.getLanguage();
    }

    public String getCode() {
        return sourceCode.getSourceCode();
    }

    public void updateTempCode(String code) {
        sourceCode.updateSourceCode(code);
    }

    public static TempCode create(Language language, String code, SessionDetail sessionDetail) {
        return TempCode.builder()
                .language(language)
                .code(code)
                .sessionDetail(sessionDetail)
                .build();
    }

    @Builder
    private TempCode(SessionDetail sessionDetail, Language language, String code) {
        this.sessionDetail = sessionDetail;
        this.sourceCode = SourceCode.of(code, language);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TempCode tempCode = (TempCode) o;

        return getLanguage() == tempCode.getLanguage();
    }
    @Override
    public int hashCode() {
        return getLanguage() != null ? getLanguage().hashCode() : 0;
    }
}
