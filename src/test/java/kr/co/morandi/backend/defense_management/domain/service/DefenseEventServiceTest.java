package kr.co.morandi.backend.defense_management.domain.service;

import kr.co.morandi.backend.IntegrationTestSupport;
import kr.co.morandi.backend.defense_management.application.service.timer.DefenseTimerService;
import kr.co.morandi.backend.defense_management.domain.event.DefenseStartTimerEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;

class DefenseEventServiceTest extends IntegrationTestSupport {

    @Autowired
    private ApplicationEventPublisher publisher;

    @MockBean
    private DefenseTimerService defenseTimerService;

    @Autowired
    private DefenseEventService defenseEventService;

    @Autowired
    private TransactionTemplate transactionTemplate;


    @DisplayName("DefenseStartTimerEvent가 발생하면 DefenseTimerService의 startDefenseTimer가 호출된다.")
    @Test
    void onDefenseStartTimerEvent() {
        // given
        LocalDateTime startDateTime = LocalDateTime.of(2021, 10, 1, 0, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2021, 10, 1, 0, 1, 0);
        DefenseStartTimerEvent event = new DefenseStartTimerEvent(1L, startDateTime, endDateTime);

        // when
        transactionTemplate.execute(status -> {
            publisher.publishEvent(event);
            return null;
        });

        // then
        verify(defenseTimerService, times(1))
                .startDefenseTimer(1L, startDateTime, endDateTime);
    }

    @DisplayName("DefenseStartTimerEvent 발행 후 rollback되면 DefenseTimerService의 startDefenseTimer가 호출되지 않는다.")
    @Test
    void onDefenseStartTimerEventRollback() {
        // given
        LocalDateTime startDateTime = LocalDateTime.of(2021, 10, 1, 0, 0, 0);
        LocalDateTime endDateTime = LocalDateTime.of(2021, 10, 1, 0, 1, 0);
        DefenseStartTimerEvent event = new DefenseStartTimerEvent(1L, startDateTime, endDateTime);

        // when
        transactionTemplate.execute(status -> {
            publisher.publishEvent(event);
            status.setRollbackOnly();
            return null;
        });

        // then
        verify(defenseTimerService, never())
                .startDefenseTimer(1L, startDateTime, endDateTime);
    }

}