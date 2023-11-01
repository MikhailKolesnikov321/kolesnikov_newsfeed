package com.example.kolesnikov_advancedServer.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class LoginUserDto {
    private String avatar;
    private String email;
    private UUID id;
    private String name;
    private String role = "user";
    private String token;
}
