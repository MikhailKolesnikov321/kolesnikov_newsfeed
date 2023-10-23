package com.example.kolesnikov_advancedServer.service;

import com.example.kolesnikov_advancedServer.dto.LoginUserDto;
import com.example.kolesnikov_advancedServer.dto.RegisterUserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public LoginUserDto register(RegisterUserDto registerUserDto);
}
