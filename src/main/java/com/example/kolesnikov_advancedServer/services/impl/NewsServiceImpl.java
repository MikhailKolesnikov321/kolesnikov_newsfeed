package com.example.kolesnikov_advancedServer.services.impl;

import com.example.kolesnikov_advancedServer.dtos.BaseSuccessResponse;
import com.example.kolesnikov_advancedServer.dtos.GetNewsOutDto;
import com.example.kolesnikov_advancedServer.dtos.NewsDto;
import com.example.kolesnikov_advancedServer.dtos.PageableResponse;
import com.example.kolesnikov_advancedServer.entities.NewsEntity;
import com.example.kolesnikov_advancedServer.entities.TagEntity;
import com.example.kolesnikov_advancedServer.entities.UserEntity;
import com.example.kolesnikov_advancedServer.exceptions.CustomException;
import com.example.kolesnikov_advancedServer.mappers.NewsMapper;
import com.example.kolesnikov_advancedServer.repositories.NewsRepo;
import com.example.kolesnikov_advancedServer.repositories.TagsRepo;
import com.example.kolesnikov_advancedServer.repositories.UserRepo;
import com.example.kolesnikov_advancedServer.services.NewsService;
import com.example.kolesnikov_advancedServer.validations.ErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final NewsRepo newsRepo;
    private final UserRepo userRepo;

    @Override
    public Long createNews(NewsDto newsDto, UUID id) {
        UserEntity userEntity = userRepo.findById(id)
                .orElseThrow(()-> new CustomException(ErrorCodes.USER_NOT_FOUND));
        NewsEntity newsEntity = NewsMapper.INSTANCE.newsDtoToNewsEntity(newsDto);
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

    @Override
    public PageableResponse getNews(int page, int perPage){
        PageRequest pageable = PageRequest.of(page-1, perPage);
        Page<NewsEntity> newsPage = newsRepo.findAll(pageable);
        List<GetNewsOutDto> newsDtos = newsPage.getContent().stream()
                .map(NewsMapper.INSTANCE::newsEntityToGetNewsOutDto)
                .collect(Collectors.toList());
        return PageableResponse.ok(newsDtos, newsPage.getSize());
    }

    @Override
    public PageableResponse getNewsByParam(int page, int perPage, String username, String keyword, List<String> tags) {
        PageRequest pageable = PageRequest.of(page - 1, perPage);
        Page<NewsEntity> newsPage = newsRepo.findByParams(username, keyword, tags, pageable);
        List<GetNewsOutDto> newsDtos = newsPage.getContent().stream()
                .map(NewsMapper.INSTANCE::newsEntityToGetNewsOutDto)
                .collect(Collectors.toList());
        return PageableResponse.ok(newsDtos, newsPage.getNumberOfElements());
    }

    @Override
    public BaseSuccessResponse changeNewsData(Long id, NewsDto newsDto){
        NewsEntity newsEntity = newsRepo.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCodes.NEWS_NOT_FOUND));
        newsEntity.setDescription(newsDto.getDescription());
        newsEntity.setImage(newsDto.getImage());
        newsEntity.setTitle(newsDto.getTitle());
        List<TagEntity> existingTags = newsEntity.getTags();
        List<TagEntity> newTags = new ArrayList<>();

        for (String tagTitle : newsDto.getTags()) {
            TagEntity tagEntity = existingTags.stream()
                    .filter(existingTag -> existingTag.getTitle().equals(tagTitle))
                    .findFirst()
                    .orElse(new TagEntity());

            tagEntity.setTitle(tagTitle);
            tagEntity.setNews(newsEntity);
            newTags.add(tagEntity);
        }

        existingTags.clear();
        existingTags.addAll(newTags);
        newsEntity.setTags(existingTags);
        newsRepo.save(newsEntity);
        return BaseSuccessResponse.ok();
    }

    @Override
    public BaseSuccessResponse deleteNews(Long id){
        NewsEntity newsEntity = newsRepo.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCodes.NEWS_NOT_FOUND));
        newsRepo.delete(newsEntity);
        return BaseSuccessResponse.ok();
    }
}
