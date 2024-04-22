package kr.co.morandi.backend.member_management.infrastructure.config.security;

import kr.co.morandi.backend.member_management.infrastructure.filter.oauth.JwtAuthenticationFilter;
import kr.co.morandi.backend.member_management.infrastructure.filter.oauth.JwtExceptionFilter;
import kr.co.morandi.backend.member_management.infrastructure.filter.oauth.RequestCachingFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final RequestCachingFilter requestCachingFilter;
    private final CorsConfigurationSource corsConfigurationSource;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .httpBasic(HttpBasicConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/oauths/**","/swagger-ui/**", "/swagger-resources/**",
                                "/v3/api-docs/**").permitAll()
                        .requestMatchers("/daily-record/rankings/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/daily-defense/**").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class)
                .addFilterBefore(requestCachingFilter, JwtExceptionFilter.class);

        return http.build();
    }
}
