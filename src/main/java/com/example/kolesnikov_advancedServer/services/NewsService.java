package com.example.kolesnikov_advancedServer.services;

import com.example.kolesnikov_advancedServer.dtos.BaseSuccessResponse;
import com.example.kolesnikov_advancedServer.dtos.NewsDto;
import com.example.kolesnikov_advancedServer.dtos.PageableResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface NewsService {

    Long createNews (NewsDto newsDto, UUID id);

    PageableResponse getNews(int page, int perPage);

    PageableResponse getNewsByParam(int page, int perPage, String username, String keyword, List<String> tags);

    BaseSuccessResponse changeNewsData(Long id, NewsDto newsDto);

    BaseSuccessResponse deleteNews(Long id);
}
