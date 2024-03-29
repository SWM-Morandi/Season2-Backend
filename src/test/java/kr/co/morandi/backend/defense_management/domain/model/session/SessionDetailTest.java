package kr.co.morandi.backend.defense_management.domain.model.session;

import kr.co.morandi.backend.defense_management.domain.model.session.DefenseSession;
import kr.co.morandi.backend.defense_management.domain.model.session.SessionDetail;
import kr.co.morandi.backend.defense_management.domain.model.tempcode.model.TempCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language.CPP;
import static kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language.JAVA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.Mockito.mock;

@ActiveProfiles("test")
class SessionDetailTest {
    @DisplayName("처음 SessionDetail을 생성하면 tempCode 언어는 CPP로 생성된다.")
    @Test
    void createSessionDetailWithInitialLanguageType() {
        // given
        DefenseSession defenseSession = mock(DefenseSession.class);

        // when
        final SessionDetail sessionDetail = SessionDetail.create(defenseSession, 1L);

        // then
        assertThat(sessionDetail.getTempCodes())
                .hasSize(1)
                .extracting("language", "sessionDetail")
                .contains(
                        tuple(CPP, sessionDetail)
                );

    }
    @DisplayName("SessionDetail에 저장된 언어로 tempCode를 조회할 수 있다.")
    @Test
    void getTempCode() {
        // given
        DefenseSession defenseSession = mock(DefenseSession.class);
        final SessionDetail sessionDetail = SessionDetail.create(defenseSession, 1L);

        // when
        final TempCode tempCode = sessionDetail.getTempCode(CPP);

        // then
        assertThat(tempCode)
                .extracting("language","sessionDetail")
                .contains(CPP, sessionDetail);
    }

    @DisplayName("SessionDetail에 저장되지 않은 언어로 tempCode를 조회하면 새로 생성된다.")
    @Test
    void getTempCodeWhenNotExists() {
        // given
        DefenseSession defenseSession = mock(DefenseSession.class);
        final SessionDetail sessionDetail = SessionDetail.create(defenseSession, 1L);

        // when
        final TempCode tempCode = sessionDetail.getTempCode(JAVA);

        // then
        assertThat(tempCode)
                .extracting("language","sessionDetail")
                .contains(JAVA, sessionDetail);
        assertThat(sessionDetail.getTempCodes()).hasSize(2)
                .extracting("language","sessionDetail")
                .contains(
                        tuple(CPP, sessionDetail),
                        tuple(JAVA, sessionDetail)
                );
    }

    @DisplayName("SessionDetail에 저장된 언어로 tempCode에 접근하여 수정할 수 있다.")
    @Test
    void updateTempCode() {
        // given
        DefenseSession defenseSession = mock(DefenseSession.class);
        final SessionDetail sessionDetail = SessionDetail.create(defenseSession, 1L);

        // when
        sessionDetail.updateTempCode(CPP, "newCode");

        // then
        assertThat(sessionDetail.getTempCode(CPP)
                .getCode())
                .isEqualTo("newCode");

    }

    @DisplayName("SessionDetail에 저장되지 않은 언어로 tempCode를 수정하면 새로 생성된다.")
    @Test
    void updateTempCodeWhenNotExists() {
        // given
        DefenseSession defenseSession = mock(DefenseSession.class);
        final SessionDetail sessionDetail = SessionDetail.create(defenseSession, 1L);

        // when
        sessionDetail.updateTempCode(JAVA, "newCode");

        // then
        assertThat(sessionDetail.getTempCodes()).hasSize(2)
                    .extracting("language","sessionDetail")
                    .contains(
                            tuple(CPP, sessionDetail),
                            tuple(JAVA, sessionDetail)
                    );

        assertThat(sessionDetail.getTempCode(JAVA)
                .getCode())
                .isEqualTo("newCode");

    }
    @DisplayName("SessionDetail에 tempCode를 원하는 언어로 추가할 수 있다.")
    @Test
    void addTempCode() {
        // given
        DefenseSession defenseSession = mock(DefenseSession.class);
        final SessionDetail sessionDetail = SessionDetail.create(defenseSession, 1L);

        // when
        sessionDetail.addTempCode(JAVA, JAVA.getInitialCode());

        // then
        assertThat(sessionDetail.getTempCodes()).hasSize(2)
                .extracting("language","sessionDetail")
                .contains(
                        tuple(CPP, sessionDetail),
                        tuple(JAVA, sessionDetail)
                );

    }
}