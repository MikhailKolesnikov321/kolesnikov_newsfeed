package com.example.kolesnikov_advancedServer.logs;

import com.example.kolesnikov_advancedServer.entities.LogEntity;
import com.example.kolesnikov_advancedServer.repositories.LogRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class LoggerInterceptor implements HandlerInterceptor {

    private final LogRepo logRepo;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LogEntity log = new LogEntity();
        log.setTimestamp(LocalDateTime.now());
        log.setMethodName(request.getMethod());
        log.setUrl(request.getRequestURI());
        log.setIp(request.getRemoteAddr());
        log.setUserAgent(request.getHeader("User-Agent"));
        log.setResponseStatus(response.getStatus());
        logRepo.save(log);
    }
}
