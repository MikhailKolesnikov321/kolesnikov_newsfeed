package com.example.kolesnikov_advancedServer.mappers;

import com.example.kolesnikov_advancedServer.dtos.LoginUserDto;
import com.example.kolesnikov_advancedServer.dtos.RegisterUserDto;
import com.example.kolesnikov_advancedServer.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AuthMappers {
    AuthMappers INSTANCE = Mappers.getMapper(AuthMappers.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "token", ignore = true)
    UserEntity RegisterUserDtoToUserEntity(RegisterUserDto registerUserDto);

    LoginUserDto UserEntityToLoginUserDto(UserEntity user);
}
