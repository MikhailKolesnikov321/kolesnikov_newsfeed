package com.example.kolesnikov_advancedServer.mappers;

import com.example.kolesnikov_advancedServer.dto.LoginUserDto;
import com.example.kolesnikov_advancedServer.dto.RegisterUserDto;
import com.example.kolesnikov_advancedServer.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMappers {
    UserMappers INSTANCE = Mappers.getMapper(UserMappers.class);
    @Mapping(target = "id", ignore = true)
    UserEntity RegisterUserDtoToUserEntity (RegisterUserDto registerUserDto);
    LoginUserDto UserEntityToLoginUserDto(UserEntity user);
}
