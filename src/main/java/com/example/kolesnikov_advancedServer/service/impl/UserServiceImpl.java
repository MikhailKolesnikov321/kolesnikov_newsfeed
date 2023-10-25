package com.example.kolesnikov_advancedServer.service.impl;

import com.example.kolesnikov_advancedServer.JwtToken.JwtTokenProvider;
import com.example.kolesnikov_advancedServer.dto.AuthUserDto;
import com.example.kolesnikov_advancedServer.dto.LoginUserDto;
import com.example.kolesnikov_advancedServer.dto.RegisterUserDto;
import com.example.kolesnikov_advancedServer.entity.UserEntity;
import com.example.kolesnikov_advancedServer.exceptions.CustomException;
import com.example.kolesnikov_advancedServer.validation.ErrorCodes;
import com.example.kolesnikov_advancedServer.mappers.UserMappers;
import com.example.kolesnikov_advancedServer.repository.UserRepo;
import com.example.kolesnikov_advancedServer.service.UserService;
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