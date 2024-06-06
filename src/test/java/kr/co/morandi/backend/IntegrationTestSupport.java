package kr.co.morandi.backend;

import kr.co.morandi.backend.config.WebClientTestConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@Import({
        WebClientTestConfig.class
})
public abstract class IntegrationTestSupport {

}
