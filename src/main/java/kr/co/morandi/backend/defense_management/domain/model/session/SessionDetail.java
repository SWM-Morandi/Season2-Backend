package kr.co.morandi.backend.defense_management.domain.model.session;

import jakarta.persistence.*;
import kr.co.morandi.backend.common.model.BaseEntity;
import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language;
import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.TempCode;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SessionDetail extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    private DefenseSession defenseSession;

    @OneToMany(mappedBy = "sessionDetail", cascade = CascadeType.ALL)
    private Set<TempCode> tempCodes = new HashSet<>();

    private Long problemNumber;

    private Language lastAccessLanguage;

    public static final Language INITIAL_LANGUAGE = Language.CPP;
    public static SessionDetail create(DefenseSession defenseSession, Long problemNumber) {
        return new SessionDetail(defenseSession, problemNumber);
    }
    public TempCode getTempCode(Language language) {
        Optional<TempCode> maybeTempCode = getTempCodes().stream()
                .filter(tempcode -> tempcode.getLanguage().equals(language))
                .findFirst();

        return maybeTempCode.orElseGet(() -> addTempCode(language, language.getInitialCode()));
    }

    public TempCode addTempCode(Language language, String code) {
        TempCode tempCode = TempCode.create(language, code, this);
        getTempCodes().add(tempCode);

        return tempCode;
    }

    /*
    * 만약 없는 언어로 tempCode를 update하려고 했더라도
    * addTempCode를 호출해서 추가하고, 예외를 반환하지 않는다.
    * */
    public void updateTempCode(Language language, String code) {
        this.lastAccessLanguage = language;
        final Optional<TempCode> tempCode = getTempCodes().stream()
                .filter(tempcode -> tempcode.getLanguage().equals(language))
                .findFirst();

        if(tempCode.isPresent()) {
            tempCode.get().updateTempCode(code);
            return;
        }
        // 만약 없는 언어로 tempCode를 update하려고 했으면 addTempCode를 호출해서 추가해준다.
        addTempCode(language, code);
    }

    private SessionDetail(DefenseSession defenseSession, Long problemNumber) {
        this.defenseSession = defenseSession;
        this.problemNumber = problemNumber;
        this.lastAccessLanguage = INITIAL_LANGUAGE;
        this.tempCodes.add(TempCode.create(INITIAL_LANGUAGE, INITIAL_LANGUAGE.getInitialCode(), this));
    }
    @Builder
    private SessionDetail(DefenseSession defenseSession, Set<TempCode> tempCodes, Long problemNumber, Language lastAccessLanguage) {
        this.defenseSession = defenseSession;
        this.tempCodes = tempCodes;
        this.problemNumber = problemNumber;
        this.lastAccessLanguage = lastAccessLanguage;
    }
}
