package com.example.ticketing.server.interceptor.token.component;

import com.example.ticketing.domain.token.entity.QueueStatus;
import com.example.ticketing.server.interceptor.token.infrastructure.QueueTokenVerificationJpaRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class QueueTokenInterceptor implements HandlerInterceptor {

    private final List<String> GET_URI = List.of(
            "/api/v1/concerts/*/token"
            , "/swagger-ui/**"
            , "/v3/api-docs/**"
            , "/error"
            , "/favicon.ico"
    );

    private final List<String> POST_URI = List.of(
            "/api/v1/users/*/balance"
    );

    private final List<String> PATCH_URI = List.of(
            "/api/v1/users/*/balance"
    );

    private final Map<String, List<String>> excludePathPatternMap = Map.of(
            "GET", GET_URI,
            "POST", POST_URI,
            "PATCH", PATCH_URI
    );
    private final PathMatcher matcher = new AntPathMatcher();
    private final QueueTokenVerificationJpaRepository repository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 토큰 검증이 필요한 URI의 경우만 체크
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        log.info("Request :: [{}] [{}]", method, requestURI);

        Set<Map.Entry<String, List<String>>> entries = excludePathPatternMap.entrySet();
        for (Map.Entry<String, List<String>> entry : entries) {
            return entry.getValue().stream()
                    .anyMatch(uri -> matcher.match(uri, requestURI) && entry.getKey().equals(method));
        }

        // 토큰 검증 URI의 경우 아래 검증 로직 진행
        String authorization = request.getHeader("Authorization");
        log.info("Authorization :: [{}]", authorization);

        if (authorization == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return false;
        }

        String userUUID = request.getParameter("userUUID");
        int isExists = repository.findVerificationToken(userUUID, Integer.parseInt(authorization), QueueStatus.EXPIRED);

        if (isExists != 1) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return false;
        }

        return true;
    }

}
