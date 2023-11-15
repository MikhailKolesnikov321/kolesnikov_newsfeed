package com.example.kolesnikov_advancedServer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicUserDto {
    private String avatar;
    private String email;
    private UUID id;
    private String name;
    private String role;
}
