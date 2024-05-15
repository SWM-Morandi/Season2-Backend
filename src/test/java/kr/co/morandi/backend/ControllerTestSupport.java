package kr.co.morandi.backend;

import com.amazonaws.services.sqs.AmazonSQS;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.morandi.backend.defense_information.application.port.in.DailyDefenseUseCase;
import kr.co.morandi.backend.defense_information.infrastructure.controller.DailyDefenseController;
import kr.co.morandi.backend.defense_management.application.service.codesubmit.SQSService;
import kr.co.morandi.backend.defense_management.application.service.message.DefenseMessageService;
import kr.co.morandi.backend.defense_management.infrastructure.controller.CodeSubmitController;
import kr.co.morandi.backend.defense_management.infrastructure.controller.SessionConnectionController;
import kr.co.morandi.backend.defense_record.application.port.in.DailyRecordRankUseCase;
import kr.co.morandi.backend.defense_record.infrastructure.controller.DailyRecordController;
import kr.co.morandi.backend.member_management.infrastructure.config.cookie.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.filter.OncePerRequestFilter;

@WebMvcTest(controllers = {
        DailyDefenseController.class,
        DailyRecordController.class,
        SessionConnectionController.class,
        CodeSubmitController.class },
        excludeAutoConfiguration = SecurityAutoConfiguration.class,
        excludeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
                    OncePerRequestFilter.class
            })
        }
)
@ActiveProfiles("test")
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvc mockMvc;

    // DailyDefenseController
    @MockBean
    protected DailyDefenseUseCase dailyDefenseUseCase;

    @MockBean
    protected CookieUtils cookieUtils;

    // DailyRecordController
    @MockBean
    protected DailyRecordRankUseCase dailyRecordRankUseCase;

    //SessionConnectionController
    @MockBean
    protected DefenseMessageService defenseMessageService;

    // CodeSubmitController
    @MockBean
    protected SQSService sqsService;
}
