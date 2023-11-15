package com.example.kolesnikov_advancedServer.repositories;

import com.example.kolesnikov_advancedServer.entities.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepo extends JpaRepository<LogEntity, Long> {
}
