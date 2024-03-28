package kr.co.morandi.backend.infrastructure.adapter.defensesession;

import kr.co.morandi.backend.defense_management.application.outputport.session.DefenseSessionPort;
import kr.co.morandi.backend.defense_management.domain.model.session.DefenseSession;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.defense_management.infrastructure.persistence.session.DefenseSessionRepository;
import kr.co.morandi.backend.defense_management.infrastructure.persistence.session.SessionDetailRepository;
import kr.co.morandi.backend.member_management.infrastructure.persistence.member.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static kr.co.morandi.backend.defense_information.domain.model.defense.DefenseType.DAILY;
import static kr.co.morandi.backend.member_management.domain.model.member.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class DefenseSessionAdapterTest {

    @Autowired
    private DefenseSessionPort defenseSessionPort;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DefenseSessionRepository defenseSessionRepository;

    @Autowired
    private SessionDetailRepository sessionDetailRepository;
    @AfterEach
    void tearDown() {
        sessionDetailRepository.deleteAll();
        defenseSessionRepository.deleteAllInBatch();
        memberRepository.deleteAll();
    }

    @DisplayName("DailyDefense 세션을 조회할 수 있다.")
    @Test
    void findDailyDefenseSession() {
        // given
        Long recordId = 1L;
        Member member = createMember();

        Set<Long> problemNumbers = Set.of(2L);
        LocalDateTime startDateTime = LocalDateTime.of(2021, 10, 1, 12, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2021, 10, 1, 23, 59);

        final DefenseSession session = DefenseSession.startSession(member, recordId, DAILY, problemNumbers, startDateTime, endDateTime);
        defenseSessionPort.saveDefenseSession(session);

        // when
        final Optional<DefenseSession> dailyDefenseSession = defenseSessionPort.findTodaysDailyDefenseSession(member, startDateTime);

        // then
        assertThat(dailyDefenseSession).isPresent()
                .get()
                .extracting("lastAccessDateTime", "lastAccessProblemNumber", "endDateTime", "defenseType")
                .containsExactly(startDateTime, 2L, endDateTime, DAILY);

    }


    private Member createMember() {
        return memberRepository.save(Member.create("test", "test" + "@gmail.com", GOOGLE, "test", "test"));
    }

}