package com.example.kolesnikov_advancedServer.repositories;

import com.example.kolesnikov_advancedServer.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface TagsRepo extends JpaRepository<TagEntity, Long> {
}
