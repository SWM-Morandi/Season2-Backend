package kr.co.morandi.backend.domain.contentrecord.customdefense;

import kr.co.morandi.backend.domain.contentproblemrecord.customdefense.CustomDefenseProblemRecord;
import kr.co.morandi.backend.domain.contenttype.customdefense.CustomDefense;
import kr.co.morandi.backend.domain.contenttype.customdefense.CustomDefenseProblems;
import kr.co.morandi.backend.domain.contenttype.customdefense.CustomDefenseProblemsRepository;
import kr.co.morandi.backend.domain.contenttype.customdefense.CustomDefenseRepository;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.member.MemberRepository;
import kr.co.morandi.backend.domain.problem.Problem;
import kr.co.morandi.backend.domain.problem.ProblemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static kr.co.morandi.backend.domain.contenttype.customdefense.DefenseTier.GOLD;
import static kr.co.morandi.backend.domain.contenttype.customdefense.Visibility.OPEN;
import static kr.co.morandi.backend.domain.contenttype.tier.ProblemTier.*;
import static kr.co.morandi.backend.domain.member.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomDefenseRecordTest {

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private CustomDefenseRepository customDefenseRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CustomDefenseProblemsRepository customDefenseProblemsRepository;
    @AfterEach
    void tearDown() {
        customDefenseProblemsRepository.deleteAllInBatch();
        customDefenseRepository.deleteAllInBatch();
        problemRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }
    @DisplayName("사용자가 커스텀 디펜스 모드를 시작할 때, 푼 문제 수와 소요 시간은 0으로 설정되고 문제 개수는 커스텀 디펜스의 문제 개수와 같게 설정된다.")
    @Test
    public void init() {
        // given
        CustomDefense customDefense = makeCustomDefense();
        List<CustomDefenseProblems> customDefenseProblems = customDefense.getCustomDefenseProblems();
        List<Problem> problems = customDefenseProblems.stream().map(CustomDefenseProblems::getProblem).collect(Collectors.toList());
        Member member = Member.create("user", "user@gmail.com", GOOGLE, "user", "user");
        LocalDateTime now = LocalDateTime.of(2024, 2, 26, 0, 0, 0, 0);

        // when
        CustomDefenseRecord customDefenseRecord = CustomDefenseRecord.create(customDefense, member, now);

        // then
        assertThat(customDefenseRecord)
                .extracting("totalSolvedTime", "solvedCount", "problemCount")
                .containsExactlyInAnyOrder(0L, 0, customDefense.getProblemCount());
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