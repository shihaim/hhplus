package com.example.ticketing.server.interceptor.token.component;

import com.example.ticketing.server.interceptor.token.infrastructure.QueueTokenVerificationJpaRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RequiredArgsConstructor
public class QueueTokenInterceptor implements HandlerInterceptor {

    private final QueueTokenVerificationJpaRepository repository;

    private final String[] tokenVerificationUri = {
            ""
    };

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("requestUri :: [{}]", requestURI);

        String authorization = request.getHeader("Authorization");
        log.info("Authorization :: [{}]", authorization);

        if (authorization == null) {
            throw new AuthenticationException("존재하지 않는 토큰입니다.");
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }


}
