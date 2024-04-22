package kr.co.morandi.backend.common.config;

import kr.co.morandi.backend.common.web.resolver.MemberHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    MemberHandlerMethodArgumentResolver memberHandlerMethodArgumentResolver() {
        return new MemberHandlerMethodArgumentResolver();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(memberHandlerMethodArgumentResolver());
    }

}
