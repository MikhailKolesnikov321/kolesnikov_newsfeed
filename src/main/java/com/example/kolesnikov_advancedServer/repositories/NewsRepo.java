package com.example.kolesnikov_advancedServer.repositories;

import com.example.kolesnikov_advancedServer.entities.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface NewsRepo extends JpaRepository<NewsEntity, Long> {

}
