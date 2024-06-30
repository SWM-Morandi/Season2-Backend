package kr.co.morandi.backend.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@Configuration
public class AsyncConfig {

    // 백준 채점 API 요청을 비동기로 처리하기 위한 Executor
    @Bean(name = "submitBaekjoonApiExecutor")
    public ThreadPoolTaskExecutor baekjoonJudgementThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);  // 기본 스레드 수
        executor.setMaxPoolSize(10);  // 최대 스레드 수
        executor.setQueueCapacity(25);  // 대기열 크기
        executor.setThreadNamePrefix("baekjoon-judgement-");
        executor.initialize();
        return executor;
    }

    // 임시 코드 저장을 비동기로 처리하기 위한 Executor
    @Bean(name = "tempCodeSaveExecutor")
    public ThreadPoolTaskExecutor tempCodeSaveThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);  // 기본 스레드 수
        executor.setMaxPoolSize(10);  // 최대 스레드 수
        executor.setQueueCapacity(25);  // 대기열 크기
        executor.setThreadNamePrefix("temp-code-save-");
        executor.initialize();
        return executor;
    }

    // 채점 결과 업데이트를 비동기로 처리하기 위한 Executor
    @Bean(name = "updateJudgementStatusExecutor")
    public ThreadPoolTaskExecutor updateJudgementStatusThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);  // 기본 스레드 수
        executor.setMaxPoolSize(10);  // 최대 스레드 수
        executor.setQueueCapacity(25);  // 대기열 크기
        executor.setThreadNamePrefix("update-judgement-status-");
        executor.initialize();
        return executor;
    }

}
