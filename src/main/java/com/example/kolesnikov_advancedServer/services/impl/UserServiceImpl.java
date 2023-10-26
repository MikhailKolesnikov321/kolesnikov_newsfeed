package com.example.kolesnikov_advancedServer.services.impl;

import com.example.kolesnikov_advancedServer.JwtToken.JwtTokenProvider;
import com.example.kolesnikov_advancedServer.dtos.LoginUserDto;
import com.example.kolesnikov_advancedServer.dtos.RegisterUserDto;
import com.example.kolesnikov_advancedServer.entities.UserEntity;
import com.example.kolesnikov_advancedServer.mappers.UserMappers;
import com.example.kolesnikov_advancedServer.repositories.UserRepo;
import com.example.kolesnikov_advancedServer.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserMappers userMappers;
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public LoginUserDto register(RegisterUserDto registerUserDto) {
        registerUserDto.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        UserEntity userEntity = userMappers.RegisterUserDtoToUserEntity(registerUserDto);
        userRepo.save(userEntity);
        LoginUserDto loginUserDto = userMappers.UserEntityToLoginUserDto(userEntity);
        String token = jwtTokenProvider.generateToken(userEntity.getId().toString());
        loginUserDto.setToken(token);
        return loginUserDto;
    }
}