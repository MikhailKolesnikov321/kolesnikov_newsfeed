package com.example.kolesnikov_advancedServer;

import com.example.kolesnikov_advancedServer.dtos.BaseSuccessResponse;
import com.example.kolesnikov_advancedServer.dtos.GetNewsOutDto;
import com.example.kolesnikov_advancedServer.dtos.NewsDto;
import com.example.kolesnikov_advancedServer.dtos.PageableResponse;
import com.example.kolesnikov_advancedServer.entities.NewsEntity;
import com.example.kolesnikov_advancedServer.entities.TagEntity;
import com.example.kolesnikov_advancedServer.entities.UserEntity;
import com.example.kolesnikov_advancedServer.mappers.NewsMapper;
import com.example.kolesnikov_advancedServer.repositories.NewsRepo;
import com.example.kolesnikov_advancedServer.repositories.UserRepo;
import com.example.kolesnikov_advancedServer.services.impl.NewsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class NewsServiceTest {
    @Mock
    private NewsRepo newsRepo;

    @Mock
    private NewsMapper newsMapper;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private NewsServiceImpl newsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        newsService = new NewsServiceImpl(newsRepo,userRepo);
    }

    @Test
    void testCreateNews() {
        NewsDto newsDto = new NewsDto();
        newsDto.setDescription("Test description");
        newsDto.setImage("test-image-url");
        newsDto.setTags(List.of("tag1", "tag2"));
        newsDto.setTitle("Test News");

        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUID.randomUUID());
        userEntity.setEmail("test@example.com");

        when(userRepo.findById(any(UUID.class))).thenReturn(Optional.of(userEntity));
        when(newsMapper.newsDtoToNewsEntity(newsDto)).thenReturn(new NewsEntity());
        when(newsRepo.save(any(NewsEntity.class))).thenAnswer(invocation -> {
            NewsEntity newsEntity = invocation.getArgument(0);
            newsEntity.setId(1L);
            return newsEntity;
        });
        Long newsId = newsService.createNews(newsDto, userEntity.getId());
        assertNotNull(newsId);
    }

    @Test
    void testGetNews() {
        int page = 1;
        int perPage = 10;

        List<NewsEntity> newsEntities = Arrays.asList(new NewsEntity(), new NewsEntity());
        Page<NewsEntity> newsPage = new PageImpl<>(newsEntities);

        when(newsRepo.findAll(any(Pageable.class))).thenReturn(newsPage);

        List<GetNewsOutDto> newsDtos = Arrays.asList(new GetNewsOutDto(), new GetNewsOutDto());
        when(newsMapper.newsEntityToGetNewsOutDto(any(NewsEntity.class))).thenReturn(newsDtos.get(0), newsDtos.get(1));

        PageableResponse result = newsService.getNews(page, perPage);

        assertNotNull(result);
        assertEquals(newsDtos, result.getData());
        assertEquals(newsPage.getSize(), result.getNumberOfElements());
    }

    @Test
    void testGetNewsByParam() {
        int page = 1;
        int perPage = 10;
        String username = "testUser";
        String keyword = "testKeyword";
        List<String> tags = Arrays.asList("tag1", "tag2");

        List<NewsEntity> newsEntities = Arrays.asList(new NewsEntity(), new NewsEntity());
        Page<NewsEntity> newsPage = new PageImpl<>(newsEntities);

        when(newsRepo.findByParams(eq(username), eq(keyword), eq(tags), (PageRequest) any(Pageable.class))).thenReturn(newsPage);

        List<GetNewsOutDto> newsDtos = Arrays.asList(new GetNewsOutDto(), new GetNewsOutDto());
        when(newsMapper.newsEntityToGetNewsOutDto(any(NewsEntity.class))).thenReturn(newsDtos.get(0), newsDtos.get(1));

        PageableResponse result = newsService.getNewsByParam(page, perPage, username, keyword, tags);

        assertNotNull(result);
        assertEquals(newsDtos, result.getData());
        assertEquals(newsPage.getNumberOfElements(), result.getNumberOfElements());
    }

    @Test
    void testChangeNewsData() {
        Long newsId = 1L;
        NewsDto newsDto = new NewsDto();
        newsDto.setDescription("Updated description");
        newsDto.setImage("updated-image-url");
        newsDto.setTags(Arrays.asList("tag1", "tag2"));
        newsDto.setTitle("Updated News");

        NewsEntity newsEntity = new NewsEntity();
        newsEntity.setId(newsId);
        newsEntity.setDescription("Old description");
        newsEntity.setImage("old-image-url");
        newsEntity.setTags(Arrays.asList(new TagEntity(1L, "title1",  newsEntity), new TagEntity(2L, "title2",  newsEntity)));
        newsEntity.setTitle("Old News");

        when(newsRepo.findById(newsId)).thenReturn(Optional.of(newsEntity));
        when(newsRepo.save(any(NewsEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        BaseSuccessResponse result = newsService.changeNewsData(newsId, newsDto);

        assertNotNull(result);
        verify(newsRepo, times(1)).findById(newsId);
        verify(newsRepo, times(1)).save(newsEntity);

        assertEquals(newsDto.getDescription(), newsEntity.getDescription());
        assertEquals(newsDto.getImage(), newsEntity.getImage());
        assertEquals(newsDto.getTitle(), newsEntity.getTitle());

        List<String> tagTitles = newsEntity.getTags().stream().map(TagEntity::getTitle).collect(Collectors.toList());
        assertTrue(tagTitles.containsAll(newsDto.getTags()));
    }

    @Test
    void testDeleteNews() {
        Long newsId = 1L;
        List<TagEntity> tags = new ArrayList<>();
        NewsEntity newsEntity = new NewsEntity();
        newsEntity.setId(newsId);
        newsEntity.setTags(tags);
        when(newsRepo.findById(newsId)).thenReturn(Optional.of(newsEntity));
        newsService.deleteNews(newsId);
        verify(newsRepo, times(1)).delete(newsEntity);
    }
}
