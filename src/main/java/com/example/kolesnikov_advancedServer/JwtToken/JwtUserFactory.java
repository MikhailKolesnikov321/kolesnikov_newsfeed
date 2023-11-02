package com.example.kolesnikov_advancedServer.JwtToken;

import com.example.kolesnikov_advancedServer.entities.UserEntity;

public class JwtUserFactory {
    public static JwtUserDetails create(UserEntity userEntity) {
        return new JwtUserDetails(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getPassword()
        );
    }
}
