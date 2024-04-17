package com.example.ticketing.server.interceptor.token.component;

import com.example.ticketing.domain.token.entity.QueueToken;
import com.example.ticketing.server.interceptor.token.infrastructure.QueueTokenVerificationJpaRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class QueueTokenInterceptor implements HandlerInterceptor {

    private final Map<String, String> excludePathPatternMap = Map.of(
            "GET", "/api/v1/concerts/*/token",
            "POST", "/api/v1/users/*/balance",
            "PATCH", "/api/v1/users/*/balance"
    );
    private final PathMatcher matcher = new AntPathMatcher();
    private final QueueTokenVerificationJpaRepository repository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 토큰 검증이 필요한 URI의 경우만 체크
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        log.info("Request :: [{}] [{}]", method, requestURI);

        Set<Map.Entry<String, String>> entries = excludePathPatternMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            if (matcher.match(entry.getValue(), requestURI) && entry.getKey().equals(method)) return true;
        }

        // 토큰 검증 URI의 경우 아래 검증 로직 진행
        String authorization = request.getHeader("Authorization");
        log.info("Authorization :: [{}]", authorization);

        if (authorization == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//            throw new AuthenticationException("존재하지 않는 토큰입니다.");
        }

        String userUUID = request.getParameter("userUUID");

        QueueToken queueToken = repository.findVerificationToken(userUUID)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저입니다."));

        if (Integer.parseInt(authorization) != queueToken.getToken()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
//            throw new RuntimeException("토큰이 유효하지 않습니다.");
        }

        return true;
    }

}
