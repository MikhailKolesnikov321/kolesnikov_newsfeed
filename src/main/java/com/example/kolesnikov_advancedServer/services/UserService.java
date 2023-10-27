package com.example.kolesnikov_advancedServer.services;

import com.example.kolesnikov_advancedServer.dtos.AuthUserDto;
import com.example.kolesnikov_advancedServer.dtos.LoginUserDto;
import com.example.kolesnikov_advancedServer.dtos.RegisterUserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    LoginUserDto register(RegisterUserDto registerUserDto);

    LoginUserDto login(AuthUserDto authUserDto);
}
