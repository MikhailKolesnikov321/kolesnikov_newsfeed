package com.example.kolesnikov_advancedServer.service.impl;

import com.example.kolesnikov_advancedServer.dto.LoginUserDto;
import com.example.kolesnikov_advancedServer.dto.RegisterUserDto;
import com.example.kolesnikov_advancedServer.entity.UserEntity;
import com.example.kolesnikov_advancedServer.mappers.UserMappers;
import com.example.kolesnikov_advancedServer.repository.UserRepo;
import com.example.kolesnikov_advancedServer.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Data
@Service
public class UserServiceImpl implements UserService {
    private final UserMappers userMappers;
    private final UserEntity userEntity;
    private final UserRepo userRepo;

    public UserServiceImpl(UserMappers userMappers, UserEntity userEntity, UserRepo userRepo) {
        this.userMappers = userMappers;
        this.userEntity = userEntity;
        this.userRepo = userRepo;
    }
    @Override
    public LoginUserDto register(RegisterUserDto registerUserDto) {
         UserEntity userEntity = userMappers.RegisterUserDtoToUserEntity(registerUserDto);
        userRepo.save(userEntity);
        LoginUserDto loginUserDto = userMappers.UserEntityToLoginUserDto(userEntity);
        return loginUserDto;
    }
}