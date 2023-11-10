package com.example.kolesnikov_advancedServer.mappers;

import com.example.kolesnikov_advancedServer.dtos.GetNewsOutDto;
import com.example.kolesnikov_advancedServer.dtos.NewsDto;
import com.example.kolesnikov_advancedServer.entities.NewsEntity;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface NewsMapper {

    NewsMapper INSTANCE = Mappers.getMapper(NewsMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "user", ignore = true)
    NewsEntity newsDtoToNewsEntity(NewsDto newsDto);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "username", ignore = true)
    GetNewsOutDto newsEntityToGetNewsOutDto(NewsEntity newsEntity);

    @AfterMapping
    default void setUserIdAndUsername(NewsEntity newsEntity, @MappingTarget GetNewsOutDto getNewsOutDto) {
        if (newsEntity.getUser() != null) {
            getNewsOutDto.setUserId(newsEntity.getUser().getId());
            getNewsOutDto.setUsername(newsEntity.getUser().getName());
        }
    }
}
