package com.example.kolesnikov_advancedServer.dto;

import lombok.Data;

@Data
public class LoginUserDto {
    private String avatar;
    private String email;
    private Long id;
    private String userName;
    private String role = "user";
}
