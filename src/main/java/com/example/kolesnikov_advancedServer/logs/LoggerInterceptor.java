package com.example.kolesnikov_advancedServer.logs;

import com.example.kolesnikov_advancedServer.entities.LogEntity;
import com.example.kolesnikov_advancedServer.repositories.LogRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Enumeration;

@Component
@RequiredArgsConstructor
public class LoggerInterceptor implements HandlerInterceptor {

    private final LogRepo logRepo;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long startTime = (Long) request.getAttribute("startTime");
        long executionTime = System.currentTimeMillis() - startTime;

        LogEntity log = new LogEntity();
        log.setTimestamp(LocalDateTime.now());
        log.setMethodName(request.getMethod());
        log.setUrl(request.getRequestURI());
        log.setIp(request.getRemoteAddr());
        log.setResponseStatus(response.getStatus());
        log.setHostName(request.getServerName());
        log.setQueryParameters(request.getQueryString());
        log.setExecutionTime(executionTime);
        logRepo.save(log);
    }
}
