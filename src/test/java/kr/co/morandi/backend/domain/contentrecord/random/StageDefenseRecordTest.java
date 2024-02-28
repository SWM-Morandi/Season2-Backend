package kr.co.morandi.backend.domain.contentrecord.random;

import kr.co.morandi.backend.domain.contenttype.random.randomcriteria.RandomCriteria;
import kr.co.morandi.backend.domain.contenttype.random.randomstagedefense.RandomStageDefense;
import kr.co.morandi.backend.domain.contenttype.tier.ProblemTier;
import kr.co.morandi.backend.domain.member.Member;
import kr.co.morandi.backend.domain.member.MemberRepository;
import kr.co.morandi.backend.domain.problem.Problem;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static kr.co.morandi.backend.domain.contenttype.tier.ProblemTier.*;
import static kr.co.morandi.backend.domain.member.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class StageDefenseRecordTest {

    @Autowired
    private MemberRepository memberRepository;
    @AfterEach
    void tearDown() {
        memberRepository.deleteAllInBatch();
    }
    @DisplayName("사용자가 스테이지 모드를 시작했을 때, 스테이지 기록의 전체 소요 시간은 0분 이어야 한다.")
    @Test
    void totalSolvedTimeIsZero() {
        // given
        RandomCriteria.DifficultyRange bronzeRange = RandomCriteria.DifficultyRange.of(B5, B1);
        RandomCriteria randomCriteria = RandomCriteria.of(bronzeRange, 100L, 200L);
        RandomStageDefense randomStageDefense = RandomStageDefense.create(randomCriteria, 120L, "브론즈 스테이지 모드");
        Problem problem = Problem.create(1L, B5, 100L);
        LocalDateTime now = LocalDateTime.now();
        Member member = makeMember("user");

        // when
        StageDefenseRecord stageDefenseRecord = StageDefenseRecord.create(1L, randomStageDefense, now, member, List.of(problem));

        // then
        assertThat(stageDefenseRecord.getTotalSolvedTime()).isZero();
    }
    @DisplayName("사용자가 스테이지 모드를 시작했을 때, 스테이지 기록의 스테이지 번호는 1번이어야 한다.")
    @Test
    void stageCountIsOne() {
        // given
        RandomCriteria.DifficultyRange bronzeRange = RandomCriteria.DifficultyRange.of(B5, B1);
        RandomCriteria randomCriteria = RandomCriteria.of(bronzeRange, 100L, 200L);
        RandomStageDefense randomStageDefense = RandomStageDefense.create(randomCriteria, 120L, "브론즈 스테이지 모드");
        Problem problem = Problem.create(1L, B5, 100L);
        LocalDateTime now = LocalDateTime.now();
        Member member = makeMember("user");

        // when
        StageDefenseRecord stageDefenseRecord = StageDefenseRecord.create(1L, randomStageDefense, now, member, List.of(problem));

        // then
        assertThat(stageDefenseRecord.getStageCount()).isOne();
    }
    @DisplayName("사용자가 스테이지 모드를 시작했을 때, 스테이지 기록의 시험 날짜는 시작한 시점과 같아야 한다.")
    @Test
    void testDateEqualNow() {
        // given
        RandomCriteria.DifficultyRange bronzeRange = RandomCriteria.DifficultyRange.of(B5, B1);
        RandomCriteria randomCriteria = RandomCriteria.of(bronzeRange, 100L, 200L);
        RandomStageDefense randomStageDefense = RandomStageDefense.create(randomCriteria, 120L, "브론즈 스테이지 모드");
        Problem problem = Problem.create(1L, B5, 100L);
        LocalDateTime now = LocalDateTime.now();
        Member member = makeMember("user");

        // when
        StageDefenseRecord stageDefenseRecord = StageDefenseRecord.create(1L, randomStageDefense, now, member, List.of(problem));

        // then
        assertThat(stageDefenseRecord.getTestDate()).isEqualTo(now);
    }
    private Member makeMember(String name) {
        Member member = Member.create(name, name + "@gmail.com", GOOGLE, name, name);
        return memberRepository.save(member);
    }
}