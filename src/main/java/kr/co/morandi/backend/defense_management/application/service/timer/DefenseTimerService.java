    package kr.co.morandi.backend.defense_management.application.service.timer;

    import kr.co.morandi.backend.common.exception.MorandiException;
    import kr.co.morandi.backend.defense_management.domain.error.SessionErrorCode;
    import kr.co.morandi.backend.defense_management.domain.service.SessionService;
    import kr.co.morandi.backend.defense_record.domain.error.RecordErrorCode;
    import lombok.RequiredArgsConstructor;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.stereotype.Service;

    import java.time.Duration;
    import java.time.LocalDateTime;
    import java.util.concurrent.Executors;
    import java.util.concurrent.ScheduledExecutorService;
    import java.util.concurrent.TimeUnit;

    @Service
    @Slf4j
    @RequiredArgsConstructor
    public class DefenseTimerService {

        private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        private final SessionService sessionService;

        public void startDefenseTimer(Long defenseSessionId, LocalDateTime startDateTime, LocalDateTime endDateTime) {

            long delay = Duration.between(startDateTime, endDateTime).toMillis();

            scheduler.schedule(() -> {
                try {
                    sessionService.terminateDefense(defenseSessionId);
                }
                catch (MorandiException e) {
                    //SessionErrorCode.SESSION_ALREADY_ENDED인 경우는 이미 종료된 세션이므로 무시합니다.
                    if (e.getErrorCode().equals(SessionErrorCode.SESSION_ALREADY_ENDED)) {
                        return;
                    }
                    // RecordErrorCode.RECORD_ALREADY_TERMINATED인 경우는 이미 종료된 시험기록이므로 무시합니다.
                    if (e.getErrorCode().equals(RecordErrorCode.RECORD_ALREADY_TERMINATED)) {
                        return;
                    }
                    /*
                     * 예외 발생 시 다시 큐에 넣어 5초 후에 동작하도록 만들었고
                     * 최대 3번까지 재시도하도록 만들었습니다.
                     * */
                    retryTermination(defenseSessionId, 5000, 3);
                }
                catch (Exception e) {
                    /*
                    * 예외 발생 시 다시 큐에 넣어 5초 후에 동작하도록 만들었고
                    * 최대 3번까지 재시도하도록 만들었습니다.
                    * */
                    retryTermination(defenseSessionId, 5000, 3);
                }
            }, delay, TimeUnit.MILLISECONDS);

        }

        private void retryTermination(Long defenseSessionId, long retryDelay, int retryCount) {

            /*
            * 남은 재시도 횟수가 0이하일 경우 종료
            * */
            if(retryCount <= 0) {
                log.error("재시도 횟수가 초과되어 시험 종료에 실패했습니다. defenseSessionId: {}", defenseSessionId);
                return;
            }


            scheduler.schedule(() -> {
                try {
                    sessionService.terminateDefense(defenseSessionId);
                }
                catch (MorandiException e) {
                    //SessionErrorCode.SESSION_ALREADY_ENDED인 경우는 이미 종료된 세션이므로 무시합니다.
                    if (e.getErrorCode().equals(SessionErrorCode.SESSION_ALREADY_ENDED)) {
                        return;
                    }
                    // RecordErrorCode.RECORD_ALREADY_TERMINATED인 경우는 이미 종료된 시험기록이므로 무시합니다.
                    if (e.getErrorCode().equals(RecordErrorCode.RECORD_ALREADY_TERMINATED)) {
                        return;
                    }
                    retryTermination(defenseSessionId, retryDelay, retryCount - 1);
                }
                catch (Exception e) {
                    /*
                    * 남은 재시도 횟수를 1 감소시키면서 재시도합니다.
                    * */
                    retryTermination(defenseSessionId, retryDelay, retryCount - 1);
                }
            }, retryDelay, TimeUnit.MILLISECONDS);
        }

    }
