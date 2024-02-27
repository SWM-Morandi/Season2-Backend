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
import org.springframework.test.context.ActiveProfiles;

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
@ActiveProfiles("test")
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
    void isSolvedFalse() {
        // given
        CustomDefense customDefense = makeCustomDefense();
        List<Problem> problems = makeCustomProblems(customDefense);
        Member member = makeMember("user");
        CustomDefenseRecord customDefenseRecord = makeCustomDefenseRecord(customDefense, member);

        // when
        List<CustomDefenseProblemRecord> customDefenseProblemRecords = problems.stream()
                .map(problem -> CustomDefenseProblemRecord.create(customDefense, customDefenseRecord, member, problem))
                .collect(Collectors.toList());

        // then
        assertThat(customDefenseProblemRecords)
                .extracting("isSolved")
                .containsExactlyInAnyOrder(false, false);
    }

    @DisplayName("사용자가 커스텀 랜덤 디펜스를 시작할 때, 각각의 문제 기록의 소요 시간은 0분이다.")
    @Test
    void solvedTimeIsZero() {
        // given
        CustomDefense customDefense = makeCustomDefense();
        List<Problem> problems = makeCustomProblems(customDefense);
        Member member = makeMember("user");
        CustomDefenseRecord customDefenseRecord = makeCustomDefenseRecord(customDefense, member);

        // when
        List<CustomDefenseProblemRecord> customDefenseProblemRecords = problems.stream()
                .map(problem -> CustomDefenseProblemRecord.create(customDefense, customDefenseRecord, member, problem))
                .collect(Collectors.toList());

        // then
        assertThat(customDefenseProblemRecords)
                .extracting("solvedTime")
                .containsExactlyInAnyOrder(0L, 0L);
    }

    @DisplayName("사용자가 커스텀 랜덤 디펜스를 시작할 때, 각각의 문제 제출 횟수는 0회이다.")
    @Test
    void submitCountIsZero() {
        // given
        CustomDefense customDefense = makeCustomDefense();
        List<Problem> problems = makeCustomProblems(customDefense);
        Member member = makeMember("user");
        CustomDefenseRecord customDefenseRecord = makeCustomDefenseRecord(customDefense, member);

        // when
        List<CustomDefenseProblemRecord> customDefenseProblemRecords = problems.stream()
                .map(problem -> CustomDefenseProblemRecord.create(customDefense, customDefenseRecord, member, problem))
                .collect(Collectors.toList());

        // then
        assertThat(customDefenseProblemRecords)
                .extracting("submitCount")
                .containsExactlyInAnyOrder(0L, 0L);
    }

    @DisplayName("사용자가 커스텀 랜덤 디펜스를 시작할 때, 각각의 문제 정답 코드는 null 값이다.")
    @Test
    void solvedCodeIsNull() {
        // given
        CustomDefense customDefense = makeCustomDefense();
        List<Problem> problems = makeCustomProblems(customDefense);
        Member member = makeMember("user");
        CustomDefenseRecord customDefenseRecord = makeCustomDefenseRecord(customDefense, member);

        // when
        List<CustomDefenseProblemRecord> customDefenseProblemRecords = problems.stream()
                .map(problem -> CustomDefenseProblemRecord.create(customDefense, customDefenseRecord, member, problem))
                .collect(Collectors.toList());

        // then
        assertThat(customDefenseProblemRecords)
                .extracting("solvedCode")
                .containsExactlyInAnyOrder(null, null);
    }

    private CustomDefense makeCustomDefense() {
        Member member = makeMember("author");
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
    private List<Problem> makeCustomProblems(CustomDefense customDefense) {
        List<CustomDefenseProblems> customDefenseProblems = customDefense.getCustomDefenseProblems();
        List<Problem> problems = customDefenseProblems.stream().map(CustomDefenseProblems::getProblem).collect(Collectors.toList());
        return problems;
    }
    private Member makeMember(String name) {
        Member member = Member.create(name, name + "@gmail.com", GOOGLE, name, name);
        return memberRepository.save(member);
    }
    private CustomDefenseRecord makeCustomDefenseRecord(CustomDefense customDefense, Member member) {
        LocalDateTime now = LocalDateTime.of(2024, 2, 26, 0, 0, 0, 0);
        CustomDefenseRecord customDefenseRecord = CustomDefenseRecord.create(customDefense, member, now);
        customDefenseRecordRepository.save(customDefenseRecord);
        return customDefenseRecord;
    }
}