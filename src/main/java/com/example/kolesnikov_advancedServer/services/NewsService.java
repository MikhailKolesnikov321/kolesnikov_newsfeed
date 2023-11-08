package com.example.kolesnikov_advancedServer.services;

import com.example.kolesnikov_advancedServer.dtos.NewsDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface NewsService {

    Long createNews (NewsDto newsDto, UUID id);
}
