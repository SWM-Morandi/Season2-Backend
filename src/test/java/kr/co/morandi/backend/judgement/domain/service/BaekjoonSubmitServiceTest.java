package kr.co.morandi.backend.judgement.domain.service;

import kr.co.morandi.backend.IntegrationTestSupport;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.defense_information.infrastructure.persistence.dailydefense.DailyDefenseRepository;
import kr.co.morandi.backend.defense_record.domain.model.dailydefense_record.DailyRecord;
import kr.co.morandi.backend.defense_record.infrastructure.persistence.dailydefense_record.DailyRecordRepository;
import kr.co.morandi.backend.factory.TestDefenseFactory;
import kr.co.morandi.backend.factory.TestMemberFactory;
import kr.co.morandi.backend.factory.TestProblemFactory;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.result.BaekjoonJudgementResult;
import kr.co.morandi.backend.judgement.domain.model.baekjoon.submit.BaekjoonSubmit;
import kr.co.morandi.backend.judgement.domain.model.submit.SubmitCode;
import kr.co.morandi.backend.judgement.domain.model.submit.SubmitVisibility;
import kr.co.morandi.backend.judgement.infrastructure.persistence.submit.BaekjoonSubmitRepository;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.member_management.infrastructure.persistence.member.MemberRepository;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import kr.co.morandi.backend.problem_information.infrastructure.persistence.problem.ProblemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.AopTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

import static kr.co.morandi.backend.defense_management.domain.model.tempcode.model.Language.JAVA;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
class BaekjoonSubmitServiceTest extends IntegrationTestSupport {

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

    @Autowired
    private BaekjoonSubmitService baekjoonSubmitService;

    @DisplayName("채점 결과를 업데이트할 수 있다.")
    @Test
    void canUpdateJudgementStatusCorrectly() {

        baekjoonSubmitService = AopTestUtils.getTargetObject(baekjoonSubmitService);

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

        SubmitCode 제출할_코드 = SubmitCode.builder()
                .sourceCode("code")
                .language(JAVA)
                .build();

        BaekjoonSubmit 백준_제출 = BaekjoonSubmit.builder()
                .submitCode(제출할_코드)
                .member(사용자)
                .detail(dailyRecord.getDetail(1L))
                .submitVisibility(SubmitVisibility.OPEN)
                .trialNumber(1)
                .build();

        final BaekjoonSubmit 저장된_백준_제출 = baekjoonSubmitRepository.save(백준_제출);

        final BaekjoonJudgementResult 백준_채점_디테일_정보 = BaekjoonJudgementResult.defaultResult();

        // when
        baekjoonSubmitService.asyncUpdateJudgementStatus(저장된_백준_제출.getSubmitId(), 512, 120, 백준_채점_디테일_정보);

        final Optional<BaekjoonSubmit> maybeBaekjoonSubmit = baekjoonSubmitRepository.findById(저장된_백준_제출.getSubmitId());

        assertThat(maybeBaekjoonSubmit).isPresent()
                .get()
                .isNotNull()
                .extracting("judgementResult.memory", "judgementResult.time", "baekjoonJudgementResult.subtaskScore", "baekjoonJudgementResult.partialScore", "baekjoonJudgementResult.ac", "baekjoonJudgementResult.tot")
                .containsExactly(512, 120, 백준_채점_디테일_정보.getSubtaskScore(), 백준_채점_디테일_정보.getPartialScore(), 백준_채점_디테일_정보.getAc(), 백준_채점_디테일_정보.getTot());

    }

}