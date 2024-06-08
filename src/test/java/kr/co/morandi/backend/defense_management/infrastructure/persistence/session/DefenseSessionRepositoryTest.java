package kr.co.morandi.backend.defense_management.infrastructure.persistence.session;

import kr.co.morandi.backend.IntegrationTestSupport;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.defense_information.infrastructure.persistence.dailydefense.DailyDefenseRepository;
import kr.co.morandi.backend.defense_management.domain.model.session.DefenseSession;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyRecord;
import kr.co.morandi.backend.defense_record.infrastructure.persistence.dailydefense_record.DailyRecordRepository;
import kr.co.morandi.backend.factory.TestDefenseFactory;
import kr.co.morandi.backend.factory.TestMemberFactory;
import kr.co.morandi.backend.factory.TestProblemFactory;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.member_management.infrastructure.persistence.member.MemberRepository;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import kr.co.morandi.backend.problem_information.infrastructure.persistence.problem.ProblemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language.JAVA;
import static org.assertj.core.api.Assertions.assertThat;

class DefenseSessionRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private DailyDefenseRepository dailyDefenseRepository;

    @Autowired
    private DailyRecordRepository dailyRecordRepository;

    @Autowired
    private DefenseSessionRepository defenseSessionRepository;

    @DisplayName("디펜스 세션을 FetchJoin을 통해 TempCode까지 한 번에 조회할 수 있다.")
    @Test
    void findDefenseSessionJoinFetch() {
        // given
        Member 사용자 = TestMemberFactory.createMember();
        memberRepository.save(사용자);

        Map<Long, Problem> 문제 = TestProblemFactory.createProblems(5);
        problemRepository.saveAll(문제.values());

        DailyDefense 오늘의_문제 = TestDefenseFactory.createDailyDefense(문제);
        dailyDefenseRepository.save(오늘의_문제);

        Map<Long, Problem> 시도할_문제 = Map.of(1L, 문제.get(1L));

        DailyRecord 오늘의_문제_기록 = DailyRecord.builder()
                .date(LocalDateTime.of(2021, 1, 1, 0, 0))
                .problems(시도할_문제)
                .defense(오늘의_문제)
                .member(사용자)
                .build();
        dailyRecordRepository.save(오늘의_문제_기록);

        DefenseSession 디펜스_세션 = DefenseSession.builder()
                .member(사용자)
                .defenseType(오늘의_문제.getDefenseType())
                .problemNumbers(Set.of(1L))
                .recordId(오늘의_문제_기록.getRecordId())
                .startDateTime(LocalDateTime.of(2021, 1, 1, 0, 0))
                .endDateTime(LocalDateTime.of(2021, 1, 1, 1, 0))
                .build();

        디펜스_세션.updateTempCode(1L, JAVA, "exampleCode");

        defenseSessionRepository.save(디펜스_세션);

        // when
         Optional<DefenseSession> defenseSession = defenseSessionRepository.findDefenseSessionJoinFetchTempCode(디펜스_세션.getDefenseSessionId());

        // then
        assertThat(defenseSession).isPresent()
                .get()
                .extracting("defenseSessionId", "defenseType")
                .contains(디펜스_세션.getDefenseSessionId(), 오늘의_문제.getDefenseType());
        assertThat(defenseSession.get().getSessionDetail(1L).getTempCode(JAVA))
                .extracting("language", "code")
                .contains(JAVA, "exampleCode");

    }

}