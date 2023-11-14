package com.example.kolesnikov_advancedServer.configs;

import com.example.kolesnikov_advancedServer.logs.LoggerInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class LogConfig implements WebMvcConfigurer {

    private final LoggerInterceptor logInterceptor;

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor);
    }
}