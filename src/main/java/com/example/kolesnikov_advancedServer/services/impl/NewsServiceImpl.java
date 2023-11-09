package com.example.kolesnikov_advancedServer.services.impl;

import com.example.kolesnikov_advancedServer.dtos.NewsDto;
import com.example.kolesnikov_advancedServer.entities.NewsEntity;
import com.example.kolesnikov_advancedServer.entities.TagEntity;
import com.example.kolesnikov_advancedServer.entities.UserEntity;
import com.example.kolesnikov_advancedServer.exceptions.CustomException;
import com.example.kolesnikov_advancedServer.mappers.NewsMapper;
import com.example.kolesnikov_advancedServer.repositories.NewsRepo;
import com.example.kolesnikov_advancedServer.repositories.UserRepo;
import com.example.kolesnikov_advancedServer.services.NewsService;
import com.example.kolesnikov_advancedServer.validations.ErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsMapper newsMapper;
    private final NewsRepo newsRepo;
    private final UserRepo userRepo;

    @Override
    public Long createNews(NewsDto newsDto, UUID id) {
        UserEntity userEntity = userRepo.findById(id)
                .orElseThrow(()-> new CustomException(ErrorCodes.USER_NOT_FOUND));
        NewsEntity newsEntity = newsMapper.newsDtoToNewsEntity(newsDto);
        newsEntity.setUser(userEntity);
        List<NewsEntity> newsEntityList = new ArrayList<>();
        newsEntityList.add(newsEntity);
        userEntity.setNews(newsEntityList);

        List<TagEntity> tagEntities = new ArrayList<>();
        for (String tag : newsDto.getTags()) {
            TagEntity tagEntity = new TagEntity();
            tagEntity.setTitle(tag);
            tagEntity.setNews(newsEntity);
            tagEntities.add(tagEntity);
        }
        newsEntity.setTags(tagEntities);

        newsRepo.save(newsEntity);
        return newsEntity.getId();
    }
}
