package com.example.kolesnikov_advancedServer.services.impl;

import com.example.kolesnikov_advancedServer.JwtToken.JwtTokenProvider;
import com.example.kolesnikov_advancedServer.dtos.AuthDto;
import com.example.kolesnikov_advancedServer.dtos.LoginUserDto;
import com.example.kolesnikov_advancedServer.dtos.PublicUserDto;
import com.example.kolesnikov_advancedServer.dtos.PutUserDto;
import com.example.kolesnikov_advancedServer.dtos.RegisterUserDto;
import com.example.kolesnikov_advancedServer.entities.UserEntity;
import com.example.kolesnikov_advancedServer.exceptions.CustomException;
import com.example.kolesnikov_advancedServer.mappers.AuthMappers;
import com.example.kolesnikov_advancedServer.repositories.UserRepo;
import com.example.kolesnikov_advancedServer.services.UserService;
import com.example.kolesnikov_advancedServer.validations.ErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final AuthMappers userMappers;
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public LoginUserDto register(RegisterUserDto registerUserDto) {
        if(userRepo.findByEmail(registerUserDto.getEmail()).isPresent()){
            throw new CustomException(ErrorCodes.USER_ALREADY_EXISTS);
        }
        registerUserDto.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        UserEntity userEntity = userMappers.RegisterUserDtoToUserEntity(registerUserDto);
        userRepo.save(userEntity);
        LoginUserDto loginUserDto = userMappers.UserEntityToLoginUserDto(userEntity);
        String token = jwtTokenProvider.generateToken(userEntity.getId().toString());
        loginUserDto.setToken(token);
        return loginUserDto;
    }

    @Override
    public LoginUserDto login(AuthDto authUserDto){
        UserEntity userEntity = userRepo.findByEmail(authUserDto.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCodes.USER_NOT_FOUND));
        if (!passwordEncoder.matches(authUserDto.getPassword(), userEntity.getPassword())) {
            throw new CustomException(ErrorCodes.PASSWORD_NOT_VALID);
        }
        LoginUserDto loginUserDto = userMappers.UserEntityToLoginUserDto(userEntity);
        String token = jwtTokenProvider.generateToken(userEntity.getId().toString());
        loginUserDto.setToken(token);
        return loginUserDto;
    }

    @Override
    public List<PublicUserDto> getAllUsers() {
        List<UserEntity> users = userRepo.findAll();
        List<PublicUserDto> publicUserDtos = new ArrayList<>();
        if (users != null && !users.isEmpty()) {
            users.forEach(userEntity -> {
                PublicUserDto publicUserDto = userMappers.UserEntityToPublicUserDto(userEntity);
                publicUserDtos.add(publicUserDto);
            });
        }
        return publicUserDtos;
    }

    @Override
    public PublicUserDto getUserInfo(String id) {
        UserEntity userEntity = null;
        UUID uuid = null;
        try {
        uuid = UUID.fromString(id);
        } catch (IllegalArgumentException e){
            throw new CustomException(ErrorCodes.MAX_UPLOAD_SIZE_EXCEEDED);
        }
        userEntity = userRepo.findById(uuid)
                .orElseThrow(() -> new CustomException(ErrorCodes.USER_NOT_FOUND));
        return userMappers.UserEntityToPublicUserDto(userEntity);
    }

    @Override
    public PublicUserDto setUserNewData(UUID id, PutUserDto putUserDto){
        UserEntity userEntity = userRepo.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCodes.USER_NOT_FOUND));
        if (userRepo.findByEmail(putUserDto.getEmail()).isPresent()){
            throw new CustomException(ErrorCodes.USER_ALREADY_EXISTS);
        }
        userEntity.setAvatar(putUserDto.getAvatar());
        userEntity.setEmail(putUserDto.getEmail());
        userEntity.setName(putUserDto.getName());
        userEntity.setRole(putUserDto.getRole());

        userRepo.save(userEntity);

        return userMappers.UserEntityToPublicUserDto(userEntity);
    }
}