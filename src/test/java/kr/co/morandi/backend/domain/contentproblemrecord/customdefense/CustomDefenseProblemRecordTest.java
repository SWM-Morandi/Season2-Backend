package kr.co.morandi.backend.domain.contentproblemrecord.customdefense;

import kr.co.morandi.backend.domain.contentrecord.customdefense.CustomDefenseRecord;
import kr.co.morandi.backend.domain.contentrecord.customdefense.CustomDefenseRecordRepository;
import kr.co.morandi.backend.domain.contenttype.customdefense.CustomDefense;
import kr.co.morandi.backend.domain.contenttype.customdefense.CustomDefenseProblems;
import kr.co.morandi.backend.domain.contenttype.customdefense.CustomDefenseProblemsRepository;
import kr.co.morandi.backend.domain.contenttype.customdefense.CustomDefenseRepository;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.member.MemberRepository;
import kr.co.morandi.backend.domain.problem.Problem;
import kr.co.morandi.backend.domain.problem.ProblemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static kr.co.morandi.backend.domain.contenttype.customdefense.DefenseTier.GOLD;
import static kr.co.morandi.backend.domain.contenttype.customdefense.Visibility.OPEN;
import static kr.co.morandi.backend.domain.contenttype.tier.ProblemTier.B5;
import static kr.co.morandi.backend.domain.contenttype.tier.ProblemTier.S5;
import static kr.co.morandi.backend.domain.member.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CustomDefenseProblemRecordTest {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private CustomDefenseRepository customDefenseRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CustomDefenseProblemsRepository customDefenseProblemsRepository;

    @Autowired
    private CustomDefenseRecordRepository customDefenseRecordRepository;

    @Autowired
    private CustomDefenseProblemRecordRepository customDefenseProblemRecordRepository;
    @AfterEach
    void tearDown() {
        customDefenseProblemRecordRepository.deleteAllInBatch();
        customDefenseProblemsRepository.deleteAllInBatch();
        customDefenseRecordRepository.deleteAllInBatch();
        customDefenseRepository.deleteAllInBatch();
        problemRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }
    @DisplayName("사용자가 커스텀 랜덤 디펜스를 시작할 때, 정답 여부는 오답처리 되어있다.")
    @Test
    void createWithisSolved() {
        // given
        CustomDefense customDefense = makeCustomDefense();
        List<CustomDefenseProblems> customDefenseProblems = customDefense.getCustomDefenseProblems();
        List<Problem> problems = customDefenseProblems.stream().map(CustomDefenseProblems::getProblem).collect(Collectors.toList());
        Member member = Member.create("user", "user@gmail.com", GOOGLE, "user", "user");
        memberRepository.save(member);
        LocalDateTime now = LocalDateTime.of(2024, 2, 26, 0, 0, 0, 0);
        CustomDefenseRecord customDefenseRecord = CustomDefenseRecord.create(customDefense, member, now);
        customDefenseRecordRepository.save(customDefenseRecord);

        // when
        List<CustomDefenseProblemRecord> customDefenseProblemRecords = problems.stream()
                .map(problem -> CustomDefenseProblemRecord.create(customDefense, customDefenseRecord, member, problem))
                .collect(Collectors.toList());

        // then
        assertThat(customDefenseProblemRecords)
                .extracting("isSolved")
                .containsExactlyInAnyOrder(false, false);
    }

    private CustomDefense makeCustomDefense() {
        Member member = Member.create("author", "author@gmail.com", GOOGLE, "author", "author");
        memberRepository.save(member);

        Problem problem1 = Problem.create(1L, B5, 0L);
        Problem problem2 = Problem.create(2L, S5, 0L);
        List<Problem> problems = List.of(problem1, problem2);
        problemRepository.saveAll(problems);

        LocalDateTime now = LocalDateTime.of(2024, 2, 26, 0, 0, 0, 0);

        CustomDefense customDefense = CustomDefense.create(problems, member, "custom_defense",
                "custom_defense", OPEN, GOLD, 60L, now);

        customDefenseRepository.save(customDefense);

        return customDefense;
    }
}