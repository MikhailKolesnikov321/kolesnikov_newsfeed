package com.example.kolesnikov_advancedServer.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class LoginUserDto {
    private String avatar;
    private String email;
    private UUID id;
    private String userName;
    private String role = "user";
    private String token;
}