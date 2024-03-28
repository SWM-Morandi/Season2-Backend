package kr.co.morandi.backend.domain.record.stagerecord;

import kr.co.morandi.backend.defense_information.domain.model.defense.RandomCriteria;
import kr.co.morandi.backend.defense_information.domain.model.stagedefense.model.StageDefense;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import kr.co.morandi.backend.defense_record.domain.model.stagedefense_record.StageRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static kr.co.morandi.backend.defense_information.domain.model.defense.ProblemTier.B1;
import static kr.co.morandi.backend.defense_information.domain.model.defense.ProblemTier.B5;
import static kr.co.morandi.backend.member_management.domain.model.member.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class StageRecordTest {
    @DisplayName("스테이지 기록이 만들어졌을 때 포함된 문제 수는 1개다.")
    @Test
    void stageCountIsOne() {
        // given
        StageDefense randomStageDefense = createRandomStageDefense();

        Problem problem = Problem.create(1L, B5, 100L);
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 0, 0, 0);
        Member member = createMember("user");

        // when
        StageRecord stageDefenseRecord = StageRecord.create(randomStageDefense, startTime, member, problem);

        // then
        assertThat(stageDefenseRecord.getStageCount()).isOne();
    }
    @DisplayName("스테이지 기록이 만들어졌을 때 만들어진 첫번째 문제 기록의 스테이지 번호는 1번이어야 한다.")
    @Test
    void initialStageNumberIsSetToOne() {
        // given
        StageDefense randomStageDefense = createRandomStageDefense();

        Problem problem = Problem.create(1L, B5, 100L);
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 0, 0, 0);
        Member member = createMember("user");

        // when
        StageRecord stageDefenseRecord = StageRecord.create(randomStageDefense, startTime, member, problem);

        // then
        assertThat(stageDefenseRecord.getDetails())
                .extracting("stageNumber")
                .containsExactly(1L);
    }
    @DisplayName("스테이지 기록이 만들어졌을 때 전체 소요 시간은 0분 이어야 한다.")
    @Test
    void totalSolvedTimeIsZero() {
        // given
        StageDefense randomStageDefense = createRandomStageDefense();

        Problem problem = Problem.create(1L, B5, 100L);
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 0, 0, 0);
        Member member = createMember("user");

        // when
        StageRecord stageDefenseRecord = StageRecord.create(randomStageDefense, startTime, member, problem);

        // then
        assertThat(stageDefenseRecord.getTotalSolvedTime()).isZero();
    }
    @DisplayName("스테이지 기록이 만들어졌을 때 시험 날짜는 시작한 시점과 같아야 한다.")
    @Test
    void testDateEqualNow() {
        // given
        StageDefense randomStageDefense = createRandomStageDefense();

        Problem problem = Problem.create(1L, B5, 100L);
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 0, 0, 0);
        Member member = createMember("user");

        // when
        StageRecord stageDefenseRecord = StageRecord.create(randomStageDefense, startTime, member, problem);

        // then
        assertThat(stageDefenseRecord.getTestDate()).isEqualTo(startTime);
    }
    @DisplayName("스테이지 기록이 만들어졌을 때 만들어진 첫번째 문제 기록의 소요 시간은 0분 이어야 한다.")
    @Test
    void solvedTimeIsZero() {
        // given
        StageDefense randomStageDefense = createRandomStageDefense();

        Problem problem = Problem.create(1L, B5, 100L);
        LocalDateTime now = LocalDateTime.of(2024, 3, 1, 0, 0, 0);
        Member member = createMember("user");

        // when
        StageRecord stageDefenseRecord = StageRecord.create(randomStageDefense, now, member, problem);

        // then
        assertThat(stageDefenseRecord.getDetails())
                .extracting("solvedTime")
                .containsExactly(0L);
    }
    @DisplayName("스테이지 기록이 만들어졌을 때 만들어진 첫번째 문제 기록의 정답 여부는 오답이어야 한다.")
    @Test
    void isSolvedIsFalse() {
        // given
        StageDefense randomStageDefense = createRandomStageDefense();

        Problem problem = Problem.create(1L, B5, 100L);
        LocalDateTime now = LocalDateTime.of(2024, 3, 1, 0, 0, 0);
        Member member = createMember("user");

        // when
        StageRecord stageDefenseRecord = StageRecord.create(randomStageDefense, now, member, problem);

        // then
        assertThat(stageDefenseRecord.getDetails())
                .extracting("isSolved")
                .containsExactly(false);
    }
    @DisplayName("스테이지 기록이 만들어졌을 때 만들어진 첫번째 문제 기록의 제출 횟수는 0회 이어야한다.")
    @Test
    void submitCountIsZero() {
        // given
        StageDefense randomStageDefense = createRandomStageDefense();

        Problem problem = Problem.create(1L, B5, 100L);
        LocalDateTime now = LocalDateTime.of(2024, 3, 1, 0, 0, 0);
        Member member = createMember("user");

        // when
        StageRecord stageDefenseRecord = StageRecord.create(randomStageDefense, now, member, problem);

        // then
        assertThat(stageDefenseRecord.getDetails())
                .extracting("submitCount")
                .containsExactly(0L);
    }
    @DisplayName("스테이지 기록이 만들어졌을 때 만들어진 첫번째 문제 기록의 정답 코드는 null 이어야 한다.")
    @Test
    void solvedCodeIsNull() {
        // given
        StageDefense randomStageDefense = createRandomStageDefense();

        Problem problem = Problem.create(1L, B5, 100L);
        LocalDateTime now = LocalDateTime.of(2024, 3, 1, 0, 0, 0);
        Member member = createMember("user");

        // when
        StageRecord stageDefenseRecord = StageRecord.create(randomStageDefense, now, member, problem);

        // then
        assertThat(stageDefenseRecord.getDetails())
                .extracting("solvedCode")
                .containsExactly((String)null);
    }
    private StageDefense createRandomStageDefense() {
        RandomCriteria.DifficultyRange bronzeRange = RandomCriteria.DifficultyRange.of(B5, B1);
        RandomCriteria randomCriteria = RandomCriteria.of(bronzeRange, 100L, 200L);
        return StageDefense.create(randomCriteria, 120L, "브론즈 스테이지 모드");
    }
    private Member createMember(String name) {
        return Member.create(name, name + "@gmail.com", GOOGLE, name, name);
    }
}