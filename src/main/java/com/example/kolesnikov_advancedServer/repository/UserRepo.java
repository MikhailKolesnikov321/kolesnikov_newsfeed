package com.example.kolesnikov_advancedServer.repository;
import com.example.kolesnikov_advancedServer.dto.AuthUserDto;
import com.example.kolesnikov_advancedServer.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepo extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);
}