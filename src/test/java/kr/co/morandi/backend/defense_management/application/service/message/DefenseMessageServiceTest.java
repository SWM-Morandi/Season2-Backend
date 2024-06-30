package kr.co.morandi.backend.defense_management.application.service.message;

import kr.co.morandi.backend.IntegrationTestSupport;
import kr.co.morandi.backend.common.exception.MorandiException;
import kr.co.morandi.backend.defense_information.domain.model.dailydefense.DailyDefense;
import kr.co.morandi.backend.defense_information.infrastructure.persistence.dailydefense.DailyDefenseRepository;
import kr.co.morandi.backend.defense_management.application.request.session.StartDailyDefenseServiceRequest;
import kr.co.morandi.backend.defense_management.application.response.session.StartDailyDefenseResponse;
import kr.co.morandi.backend.defense_management.application.usecase.session.DailyDefenseManagementUsecase;
import kr.co.morandi.backend.defense_management.domain.model.session.DefenseSession;
import kr.co.morandi.backend.defense_management.infrastructure.persistence.session.DefenseSessionRepository;
import kr.co.morandi.backend.member_management.domain.model.member.Member;
import kr.co.morandi.backend.member_management.infrastructure.persistence.member.MemberRepository;
import kr.co.morandi.backend.problem_information.domain.model.problem.Problem;
import kr.co.morandi.backend.problem_information.infrastructure.persistence.problem.ProblemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static kr.co.morandi.backend.defense_information.domain.model.defense.ProblemTier.*;
import static kr.co.morandi.backend.member_management.domain.model.member.SocialType.GOOGLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
class DefenseMessageServiceTest extends IntegrationTestSupport {

    @Autowired
    private DefenseMessageService defenseMessageService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private DailyDefenseRepository dailyDefenseRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private DailyDefenseManagementUsecase dailyDefenseManagementService;

    @Autowired
    private DefenseSessionRepository defenseSessionRepository;


    /*
    * 내부에서 WebClient를 이용하는 통합 테스트에서는 ExchangeFunction의 exchange 메서드를
    * Stubbing하여 테스트를 진행합니다.
    *
    * 내부적으로 API가 호출되는 횟수만큼 Stubbing을 해주어야 합니다.
    * */
    @Autowired
    private ExchangeFunction exchangeFunction;

    @BeforeEach
    void setUp() {
        Mockito.when(exchangeFunction.exchange(Mockito.any()))
                .thenReturn(Mono.just(ClientResponse.create(HttpStatus.OK)
                        .header("Content-Type", "application/json")
                        .body("""
                        [
                            {
                                "baekjoonProblemId": 1000,
                                "title": "A+B"
                            },
                            {
                                "baekjoonProblemId": 1001,
                                "title": "A-B"
                            }
                        ]""")
                        .build()));
    }

    @DisplayName("다른 사람의 디펜스 세션에 연결을 요청하면 예외가 발생한다.")
    @Test
    void getConnectionToOtherUserDefense() {
        // given
        Member 시험_시작_사용자 = createMember();
        createDailyDefense(LocalDate.of(2021, 10, 1), "오늘의 문제 테스트");
        LocalDateTime 요청_시각 = LocalDateTime.of(2021, 10, 1, 0, 0, 0);

        StartDailyDefenseServiceRequest 시험_시작_요청 = StartDailyDefenseServiceRequest.builder()
                .problemNumber(2L)
                .build();
        final StartDailyDefenseResponse 오늘의_문제_응답 = dailyDefenseManagementService.startDailyDefense(시험_시작_요청, 시험_시작_사용자.getMemberId(), 요청_시각);

        Long 디펜스_세션_아이디 = 오늘의_문제_응답.getDefenseSessionId();

        Long 다른_사용자_아이디 = 100L;

        // when & then
        assertThatThrownBy(() -> defenseMessageService.getConnection(디펜스_세션_아이디, 다른_사용자_아이디))
                .isInstanceOf(MorandiException.class)
                .hasMessage("사용자의 시험 세션이 아닙니다.");

    }

    @DisplayName("시작된 디펜스에 연결을 요청하면 성공한다.")
    @Test
    void getConnection() {
        // given
        Member 시험_시작_사용자 = createMember();
        createDailyDefense(LocalDate.of(2021, 10, 1), "오늘의 문제 테스트");

        LocalDateTime 요청_시각 = LocalDateTime.of(2021, 10, 1, 0, 0, 0);

        StartDailyDefenseServiceRequest 시험_시작_요청 = StartDailyDefenseServiceRequest.builder()
                .problemNumber(2L)
                .build();
        final StartDailyDefenseResponse 오늘의_문제_응답 = dailyDefenseManagementService.startDailyDefense(시험_시작_요청, 시험_시작_사용자.getMemberId(), 요청_시각);

        Long 디펜스_세션_아이디 = 오늘의_문제_응답.getDefenseSessionId();

        // when
        final SseEmitter 연결 = defenseMessageService.getConnection(디펜스_세션_아이디, 시험_시작_사용자.getMemberId());

        // then
        assertThat(연결).isNotNull();
    }

    @DisplayName("이미 종료된 디펜스에 연결을 요청하면 성공한다.")
    @Test
    void getConnectionToTerminatedDefense() {
        // given
        Member 시험_시작_사용자 = createMember();
        createDailyDefense(LocalDate.of(2021, 10, 1), "오늘의 문제 테스트");

        LocalDateTime 요청_시각 = LocalDateTime.of(2021, 10, 1, 0, 0, 0);

        StartDailyDefenseServiceRequest 시험_시작_요청 = StartDailyDefenseServiceRequest.builder()
                .problemNumber(2L)
                .build();
        final StartDailyDefenseResponse 오늘의_문제_응답 = dailyDefenseManagementService.startDailyDefense(시험_시작_요청, 시험_시작_사용자.getMemberId(), 요청_시각);

        Long 디펜스_세션_아이디 = 오늘의_문제_응답.getDefenseSessionId();

        DefenseSession 디펜스_세션 = defenseSessionRepository.findById(디펜스_세션_아이디).get();
        디펜스_세션.terminateSession();
        defenseSessionRepository.save(디펜스_세션);


        // when & then
        assertThatThrownBy(() -> defenseMessageService.getConnection(디펜스_세션_아이디, 시험_시작_사용자.getMemberId()))
                .isInstanceOf(MorandiException.class)
                .hasMessage("이미 종료된 디펜스 세션입니다.");

    }

    private DailyDefense createDailyDefense(LocalDate createdDate, String contentName) {
        AtomicLong problemNumber = new AtomicLong(1L);
        Map<Long, Problem> problemMap = createProblems().stream()
                .collect(Collectors.toMap(p-> problemNumber.getAndIncrement(), problem -> problem));
        return dailyDefenseRepository.save(DailyDefense.create(createdDate, contentName, problemMap));
    }
    private List<Problem> createProblems() {
        Problem problem1 = Problem.create(1000L, B5, 0L);
        Problem problem2 = Problem.create(2000L, S5, 0L);
        Problem problem3 = Problem.create(3000L, G5, 0L);

        return problemRepository.saveAll(List.of(problem1, problem2, problem3));
    }
    private Member createMember() {
        return memberRepository.save(Member.create("test", "test" + "@gmail.com", GOOGLE, "test", "test"));
    }



}