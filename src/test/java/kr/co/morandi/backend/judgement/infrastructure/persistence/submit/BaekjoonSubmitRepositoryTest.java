package kr.co.morandi.backend.judgement.infrastructure.persistence.submit;

import kr.co.morandi.backend.IntegrationTestSupport;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.defense_information.infrastructure.persistence.dailydefense.DailyDefenseRepository;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyRecord;
import kr.co.morandi.backend.defense_record.infrastructure.persistence.dailydefense_record.DailyRecordRepository;
import kr.co.morandi.backend.factory.TestDefenseFactory;
import kr.co.morandi.backend.factory.TestMemberFactory;
import kr.co.morandi.backend.factory.TestProblemFactory;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.submit.BaekjoonSubmit;
import kr.co.morandi.backend.judgement.domain.model.submit.SourceCode;
import kr.co.morandi.backend.judgement.domain.model.submit.SubmitVisibility;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.member_management.infrastructure.persistence.member.MemberRepository;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import kr.co.morandi.backend.problem_information.infrastructure.persistence.problem.ProblemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

import static kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language.JAVA;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class BaekjoonSubmitRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private BaekjoonSubmitRepository baekjoonSubmitRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private DailyDefenseRepository dailyDefenseRepository;

    @Autowired
    private DailyRecordRepository dailyRecordRepository;

    @DisplayName("제출을 저장할 수 있다.")
    @Test
    void save() {
        // given
        Member 사용자 = TestMemberFactory.createMember();
        memberRepository.save(사용자);
        Map<Long, Problem> 문제 = TestProblemFactory.createProblems(5);
        problemRepository.saveAll(문제.values());
        DailyDefense 오늘의_문제 = TestDefenseFactory.createDailyDefense(문제);
        dailyDefenseRepository.save(오늘의_문제);

        Map<Long, Problem> 시도할_문제 = Map.of(1L, 문제.get(1L));

        DailyRecord dailyRecord = DailyRecord.builder()
                .date(LocalDateTime.of(2021, 1, 1, 0, 0))
                .problems(시도할_문제)
                .defense(오늘의_문제)
                .member(사용자)
                .build();
        dailyRecordRepository.save(dailyRecord);

        SourceCode 제출할_코드 = SourceCode.builder()
                .sourceCode("code")
                .language(JAVA)
                .build();

        BaekjoonSubmit 백준_제출 = BaekjoonSubmit.builder()
                .sourceCode(제출할_코드)
                .member(사용자)
                .detail(dailyRecord.getDetail(1L))
                .submitVisibility(SubmitVisibility.OPEN)
                .trialNumber(1)
                .build();


        // when
        final BaekjoonSubmit 저장된_백준_제출 = baekjoonSubmitRepository.save(백준_제출);

        // then
        assertThat(저장된_백준_제출)
                .isNotNull()
                .extracting("sourceCode.sourceCode", "sourceCode.language", "member", "detail", "submitVisibility", "trialNumber")
                .containsExactly("code", JAVA, 사용자, dailyRecord.getDetail(1L), SubmitVisibility.OPEN, 1);
    }

}