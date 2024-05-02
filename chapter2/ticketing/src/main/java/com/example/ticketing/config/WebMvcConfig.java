package com.example.ticketing.config;

import com.example.ticketing.server.interceptor.token.component.QueueTokenInterceptor;
import com.example.ticketing.server.interceptor.token.infrastructure.QueueTokenVerificationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final QueueTokenVerificationJpaRepository repository;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new QueueTokenInterceptor(repository));
    }
}
