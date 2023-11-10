package com.example.kolesnikov_advancedServer.repositories;

import com.example.kolesnikov_advancedServer.entities.NewsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface NewsRepo extends JpaRepository<NewsEntity, Long> {

    @Query("SELECT DISTINCT n " +
            "FROM NewsEntity n " +
            "JOIN TagEntity t ON n.id = t.news.id " +
            "WHERE (:username IS NULL OR n.user.name = :username) " +
            "AND (:keyword IS NULL OR n.description LIKE %:keyword% OR n.title LIKE %:keyword%) " +
            "AND (:tags IS NULL OR t.title in :tags)")
    Page<NewsEntity> findByParams(
            @Param("username") String username,
            @Param("keyword") String keyword,
            @Param("tags") List<String> tags,
            PageRequest pageable);

    @Query("SELECT u FROM NewsEntity u WHERE u.id = :id")
    Optional<NewsEntity> findById(@Param("id") Long id);
}
