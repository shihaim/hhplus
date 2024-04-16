package com.example.ticketing.server.interceptor.config;

import com.example.ticketing.server.interceptor.token.component.QueueTokenInterceptor;
import com.example.ticketing.server.interceptor.token.infrastructure.QueueTokenVerificationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

//https://jujeol-jujeol.github.io/2021/08/07/%ED%94%84%EB%A1%9D%EC%8B%9C-%ED%8C%A8%ED%84%B4%EC%9C%BC%EB%A1%9C-%EC%9D%B8%ED%84%B0%EC%85%89%ED%84%B0-Path%EC%99%80-Method-%EC%84%A4%EC%A0%95%ED%95%98%EA%B8%B0/
@EnableWebMvc
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final QueueTokenVerificationJpaRepository repository;

    private final static List<String> VERIFY_GET_URI = List.of(
            "/api/v1/concerts/*/token",
            "/api/v1/concerts/*",
            "/api/v1/concerts/*/seats"
    );

    private final static List<String> VERIFY_POST_URI = List.of(
            "/api/v1/payments"
    );

    private final static List<String> VERIFY_PATCH_URI = List.of(
            "/api/v1/concerts/*"
    );

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new QueueTokenInterceptor(repository))
                .addPathPatterns("/api/v1/concerts/**");
    }
}
