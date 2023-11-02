package com.example.kolesnikov_advancedServer.services;

import com.example.kolesnikov_advancedServer.dtos.AuthDto;
import com.example.kolesnikov_advancedServer.dtos.LoginUserDto;
import com.example.kolesnikov_advancedServer.dtos.PublicUserDto;
import com.example.kolesnikov_advancedServer.dtos.RegisterUserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface UserService {

    LoginUserDto register(RegisterUserDto registerUserDto);

    LoginUserDto login(AuthDto authUserDto);

    List<PublicUserDto> getAllUsers();

    PublicUserDto getUserInfoById(UUID id);

    PublicUserDto getUserInfo(UUID id);
}
