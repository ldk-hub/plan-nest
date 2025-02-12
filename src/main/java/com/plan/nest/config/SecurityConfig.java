package com.plan.nest.config;

import com.plan.nest.service.CustomOauth2UserService;
import com.plan.nest.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService; // 기본 세션 로그인
    private final CustomOauth2UserService customOAuth2UserService; // OAuth2 등록

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .headers((headers) -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/**").permitAll()
                    .anyRequest().authenticated())
            .logout((logout) -> logout
                    .logoutSuccessUrl("/"))
            .oauth2Login((oauth2) -> oauth2
                    .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                            .userService(customOAuth2UserService))
                    .defaultSuccessUrl("/", true));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
