package kr.co.morandi.backend.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;

import static org.mockito.Mockito.mock;

@TestConfiguration
public class WebClientTestConfig {
    @Bean
    @Primary
    WebClient testWebClient(ExchangeFunction exchangeFunction) {
        return WebClient.builder()
                .exchangeFunction(exchangeFunction)
                .build();
    }

    /*
     * 중요
     *
     * 내부에서 WebClient를 이용하는 통합 테스트에서는 ExchangeFunction의 exchange 메서드를
     * Stubbing하여 테스트를 진행합니다.
     *
     * 내부적으로 API가 호출되는 횟수만큼 Stubbing을 해주어야 합니다.
     * */
    @Bean
    ExchangeFunction exchangeFunction() {
        return mock(ExchangeFunction.class);
    }
}
