package com.example.kolesnikov_advancedServer.services;

import com.example.kolesnikov_advancedServer.dtos.NewsDto;
import com.example.kolesnikov_advancedServer.dtos.PageableResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface NewsService {

    Long createNews (NewsDto newsDto, UUID id);

    PageableResponse getNews(int page, int perPage);
}
